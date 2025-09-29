package com.yedam.erp.web.ApiController;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.account.AccountService;
import com.yedam.erp.service.account.JournalCloseLogService;
import com.yedam.erp.service.account.JournalService;
import com.yedam.erp.vo.account.JournalCloseLogVO;
import com.yedam.erp.vo.account.JournalVO;
import com.yedam.erp.vo.account.accountVO;
import com.yedam.erp.vo.account.invoiceVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
	
	private final AccountService accountService;
	private final JournalService journalService;  
	private final JournalCloseLogService logService;
	
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
	
	@GetMapping("/invoice")
	public List<invoiceVO> invoiceList(Long companyCode){
		companyCode = SessionUtil.companyId();
		return accountService.selectInvoice(companyCode);
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

        if (jrnNoList == null || jrnNoList.isEmpty()) {
            return ResponseEntity.badRequest().body("전표번호가 없습니다.");
        }

        int updated = journalService.updateStatusBatch(jrnNoList, status);
        return ResponseEntity.ok(updated + "건 상태 변경 완료");
    }
 // ✅ 역분개 전용 엔드포인트
    @PostMapping("/journal/reverse")
    public ResponseEntity<?> reverseJournal(@RequestBody Map<String, Object> body) {
        Long companyCode = SessionUtil.companyId();
        String originJrnNos = (String) body.get("originJrnNos"); // "JRN001,JRN002"
        String createdBy = SessionUtil.empId();

        journalService.reverseJournalCsv(companyCode, originJrnNos, createdBy);
        return ResponseEntity.ok(Map.of("success", true));
    }
    // 마감로그

@GetMapping("/journalClose/logs/{jrnNo}")
public List<JournalCloseLogVO> getLogsByJrn(@PathVariable String jrnNo) {
    Long companyCode = SessionUtil.companyId();
    return logService.getLogByJrn(companyCode, jrnNo);
}
	
}
