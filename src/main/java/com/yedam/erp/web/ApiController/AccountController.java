package com.yedam.erp.web.ApiController;

import java.util.List;

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
import com.yedam.erp.service.account.JournalService;
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
	
	
	@GetMapping("/list")
	public List<accountVO> list(String category){
		System.out.println(SessionUtil.companyId());
		System.out.println(SessionUtil.empId());
		return accountService.accountList(category);
	}
	
	@Transactional
	@PutMapping("/{acctCode}/useYN")
	public int toggleUseYn(@PathVariable("acctCode")String acctCode) {
		return accountService.updateYN(acctCode);
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
	public List<invoiceVO> list(){
		return accountService.selectInvoice();
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
    
    

    // 수정
    @PutMapping("/journal/{jrnCode}/{lineNo}")
    public int updateJournal(@RequestBody JournalVO vo, 
                                @PathVariable String jrnCode,
                                @PathVariable Long lineNo) {
        vo.setJrnCode(jrnCode);
        vo.setLineNo(lineNo);
        return journalService.updateJournal(vo);
    }
	
}
