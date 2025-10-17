package com.yedam.erp.web.ApiController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.account.AccountService;
import com.yedam.erp.service.account.BalanceSheetService;
import com.yedam.erp.service.account.InvoiceService;
import com.yedam.erp.service.account.JournalCloseLogService;
import com.yedam.erp.service.account.JournalService;
import com.yedam.erp.service.account.MoneyService;
import com.yedam.erp.service.account.PayrollAccountService;
import com.yedam.erp.service.account.ProfitStatementService;
import com.yedam.erp.service.stock.StockService;
import com.yedam.erp.vo.account.BalanceSheetVO;
import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.InvoiceLineVO;
import com.yedam.erp.vo.account.JournalCloseLogVO;
import com.yedam.erp.vo.account.JournalVO;
import com.yedam.erp.vo.account.PaymentHeaderVO;
import com.yedam.erp.vo.account.PayrollJournalVO;
import com.yedam.erp.vo.account.PayrollLineVO;
import com.yedam.erp.vo.account.ProfitStatementVO;
import com.yedam.erp.vo.account.accountVO;
import com.yedam.erp.vo.stock.OrderDetailVO;
import com.yedam.erp.vo.stock.OrderVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
	
	private final AccountService accountService;
	private final JournalService journalService;  
	private final JournalCloseLogService logService;
	private final InvoiceService invoiceService;
	private final StockService stockService;
	private final PayrollAccountService payrollService;
	private final MoneyService moneyService;
	private final ProfitStatementService profitStatementService;
	private final BalanceSheetService balanceSheetService;	
	
	@GetMapping("/list")
	public List<accountVO> list(Long companyCode){
		companyCode = SessionUtil.companyId();
		return accountService.accountList(companyCode);
	}
	
	@Transactional
	@PutMapping("/{acctCode}/useYN")
	public int toggleUseYn(@PathVariable("acctCode")String acctCode,Long companyCode) {
		companyCode = SessionUtil.companyId();
		return accountService.updateYN(acctCode,companyCode);
	}
	
	
	@PostMapping("/upload")
	public String uploadExcel( MultipartFile file) {
	    try {
	        List<accountVO> accounts = accountService.parseExcel(file);
	        int inserted = accountService.bulkInsert(accounts);
	        return inserted + "건 등록 완료";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "업로드 실패: " + e.getMessage();
	    }
	}
	

	
	 // =======================
    @GetMapping("/journal")
    public List<JournalVO> getJournalLines(Long companyCode) {
    	companyCode = SessionUtil.companyId();
        return journalService.selectJournalList(companyCode);
    }
    
    
    // 전표번호 생성 API
    @GetMapping("/nextJrnNo")
    public String getNextJrnNo() {
        return journalService.getNextJrnNo();
    }
    
    // 신규 행 저장
    @PostMapping("/journal")
    public void insertJournal(@RequestBody JournalVO vo) {
    	vo.setCompanyCode(SessionUtil.companyId());
        journalService.insertJournal(vo);
    } 
    
    // 여러건 저장 (매출전표 -> 일반전표)
    @PostMapping("/journalList")
    public void insertJournal(@RequestBody List<JournalVO> journalList) {
    	 Long companyId = SessionUtil.companyId();
    	 journalList.forEach(vo -> vo.setCompanyCode(companyId));
        journalService.insertJournalList(journalList);
    }
    
    // 일반전표 수정
    @PutMapping("/journal/{jrnCode}/{lineNo}")
    public int updateJournal(@RequestBody JournalVO vo, 
                                @PathVariable String jrnCode,
                                @PathVariable Long lineNo) {
        vo.setJrnCode(jrnCode);
        vo.setLineNo(lineNo);
        vo.setCompanyCode(SessionUtil.companyId()); 
        System.out.println("컴패니아이디 : "+ vo.getCompanyCode());
        return journalService.updateJournal(vo);
    }
    


    // 마감용 전표 리스트
    @GetMapping("/journalClose")
    public List<JournalVO> getJournalListClose() {
        Long companyCode = SessionUtil.companyId();
        return journalService.selectJournalListClose(companyCode);
    }

    // 마감용 전표 상세
    @GetMapping("/journalClose/{jrnNo}")
    public List<JournalVO> getJournalDetailClose(@PathVariable String jrnNo) {
        Long companyCode = SessionUtil.companyId();
        return journalService.selectJournalDetailClose(jrnNo, companyCode);
    }
    
 // 제출 버튼 → 상태를 submit 으로 변경
    @PutMapping("/journal/status")
    public ResponseEntity<?> updateStatusBatch(@RequestBody Map<String, Object> req) {
        @SuppressWarnings("unchecked")
        List<String> jrnNoList = (List<String>) req.get("jrnNoList");
        String status = (String) req.get("status");
        String createdBy = (String) req.get("loginName");

        if (jrnNoList == null || jrnNoList.isEmpty()) {
            return ResponseEntity.badRequest().body("전표번호가 없습니다.");
        }

        int updated = journalService.updateStatusBatch(jrnNoList, status, createdBy);
        return ResponseEntity.ok(updated + "건 상태 변경 완료");
    }
 // ✅ 역분개 전용 엔드포인트
    @PostMapping("/journal/reverse")
    public ResponseEntity<?> reverseJournal(@RequestBody Map<String, Object> body) {
        Long companyCode = SessionUtil.companyId();
        String originJrnNos = (String) body.get("originJrnNos"); // "JRN001,JRN002"
        String createdBy = SessionUtil.empName();

        journalService.reverseJournalCsv(companyCode, originJrnNos, createdBy);
        return ResponseEntity.ok(Map.of("success", true));
    }
    
    // 마감로그
	@GetMapping("/journalClose/logs/{jrnNo}")
	public List<JournalCloseLogVO> getLogsByJrn(@PathVariable String jrnNo) {
	    Long companyCode = SessionUtil.companyId();
	    return logService.getLogByJrn(companyCode, jrnNo);
	}

	// 헤더 목록 조회
	@GetMapping("/headers")
	public List<InvoiceHeaderVO> getHeaders(Long companyCode) {
		companyCode = SessionUtil.companyId();
	    return invoiceService.getInvoiceHeaders(companyCode);
	}
	
	// 특정 전표 라인 조회
	@GetMapping("/{invoiceCode}/lines")
	public List<InvoiceLineVO> getLines(@PathVariable Long invoiceCode, Long companyCode) {
		companyCode = SessionUtil.companyId();
	    return invoiceService.getInvoiceLines(invoiceCode,companyCode);
	}
	
	
	@PostMapping("/invoice")
	public Map<String, Object> saveInvoice(@RequestBody InvoiceHeaderVO header) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        invoiceService.saveInvoiceWithLines(header);

	        result.put("success", true);
	        result.put("message", "저장 성공");
	        result.put("invoiceNo", header.getInvoiceNo()); // 서비스에서 세팅된 번호
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", e.getMessage());
	    }
	    return result;
	}
	
	@GetMapping("/invoice/{invoiceCode}")
	public Map<String, Object> getInvoice(@PathVariable Long invoiceCode) {
	    Long companyCode = SessionUtil.companyId();
	    InvoiceHeaderVO header = invoiceService.getInvoiceHeader(companyCode, invoiceCode);
	    System.out.println(header);
	    List<InvoiceLineVO> lines = invoiceService.getInvoiceLines(invoiceCode, companyCode);

	    Map<String, Object> result = new HashMap<>();
	    result.put("header", header);
	    result.put("lines", lines);
	    return result;
	}
	
	// 매출매입전표 상태변경
	@PutMapping("/invoice/{invoiceCode}/posted")
	public void updatePostedFlag(
	        @PathVariable Long invoiceCode,
	        @RequestBody Map<String, String> payload) {
	    
	    String postedFlag = payload.get("postedFlag");
	    Long companyCode = SessionUtil.companyId();
	    invoiceService.updatePostedFlag(companyCode, invoiceCode, postedFlag);
	}
	
	
	@GetMapping("/orders")
	public List<OrderVO> getOrders() {
	    Long companyCode = SessionUtil.companyId();
	    return stockService.getOrderList(companyCode);
	}
	
	@GetMapping("/orders/{orderCode}/details")
	public List<OrderDetailVO> getOrderDetail(@PathVariable String orderCode) {
	    return stockService.getOrderDetailByOrderCode(orderCode);
	}
	
	
	
	// 급여전표 라인 
	 @GetMapping("/payment/lines")
	   public Map<String, List<PayrollLineVO>> getPaymentLines(@RequestParam int payrollNo, Long companyCode) {
	        companyCode = SessionUtil.companyId();
	        List<PayrollLineVO> allLines = payrollService.getDeptJournalLines(payrollNo ,companyCode);

	        Map<String, List<PayrollLineVO>> result = new HashMap<>();
	        result.put("debit", allLines.stream()
	                                    .filter(l -> "D".equalsIgnoreCase(l.getDcType()))
	                                    .toList());
	        result.put("credit", allLines.stream()
	                                     .filter(l -> "C".equalsIgnoreCase(l.getDcType()))
	                                     .toList());

	        return result;
	    }
	
	 	// ✅ 미결 전표 목록 조회
	    @GetMapping("/unpaidList")
	    public List<InvoiceHeaderVO> getUnpaidList(
	    		@RequestParam(required = false)   String type,
	    		@RequestParam(required = false)  String fromDate,
	    		@RequestParam(required = false)  String toDate,
	    		@RequestParam(required = false) String searchCus, Long companyCode) {
	    	companyCode = SessionUtil.companyId();
	        return moneyService.getUnpaidInvoices(type, fromDate, toDate, searchCus, companyCode);
	       
	    }
	    
	    // ✅ 자금전표 + 자동분개 저장
	    @PostMapping("/payment/save")
	    public ResponseEntity<?> savePayment(@RequestBody PaymentHeaderVO payment) {
	        try {
	            payment.setCompanyCode(SessionUtil.companyId());
	            List<JournalVO> journals = moneyService.savePaymentAndAutoJournal(payment);

	            return ResponseEntity.ok(Map.of(
	                "success", true,
	                "message", "자금전표 및 일반전표 자동분개 저장 완료",
	                "payCode", payment.getPayCode(),
	                "journals", journals
	            ));
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.internalServerError().body(Map.of(
	                "success", false,
	                "message", e.getMessage()
	            ));
	        }
	    }
	    
	  // 자금전표 등록
	    @PostMapping("/payrollHist/save")
	    public ResponseEntity<?> registerPayrollJournal(@RequestBody PayrollJournalVO vo) {
	        try {
	        	vo.setCreatedBy(SessionUtil.empName());
	        	vo.setCompanyCode(SessionUtil.companyId());
	            String histCode = payrollService.registerPayrollJournal(vo);
	            return ResponseEntity.ok(histCode);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.internalServerError()
	                    .body("전표 등록 중 오류: " + e.getMessage());
	        }
	    }  

	    @GetMapping("/profit-statement")
	    public List<ProfitStatementVO> getProfitStatement(
	        @RequestParam String curYm,
	        @RequestParam String prevYm, Long companyCode) {

	        Map<String, Object> param = new HashMap<>();
	        param.put("curYm", curYm);
	        param.put("prevYm", prevYm);
	        param.put("companyCode", SessionUtil.companyId());

	        return profitStatementService.getMonthlyProfit(param);
	    }
	    // 당기순손익 update 혹은 insert 
	    @PostMapping("/net-profit")
	    public ResponseEntity<Void> saveNetProfit(@RequestBody Map<String, Object> body) {
	        Long companyCode = SessionUtil.companyId();
	        String yearMonth = (String) body.get("yearMonth");
	        Double netProfitAmt = Double.parseDouble(body.get("netProfitAmt").toString());

	        Map<String, Object> param = Map.of(
	            "companyCode", companyCode,
	            "yearMonth", yearMonth,
	            "netProfitAmt", netProfitAmt
	        );

	        profitStatementService.upsertMonthlyNetProfit(param);
	        return ResponseEntity.ok().build();
	    }

	    // 재무상태표
	    @GetMapping("/balance-sheet")
	    public List<BalanceSheetVO> getBalanceSheet(@RequestParam String yearMonth) {
	        Long companyCode = SessionUtil.companyId();
	        Map<String, Object> param = new HashMap<>();
	        param.put("companyCode", companyCode);
	        param.put("yearMonth", yearMonth);
	        return balanceSheetService.selectBalanceSheet(param);
	    }
}	

	

