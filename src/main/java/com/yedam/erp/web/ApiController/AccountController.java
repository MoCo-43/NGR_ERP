package com.yedam.erp.web.ApiController;

import java.util.List;

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
    public List<JournalVO> getJournalLines() {
        return journalService.selectJournalList();
    }
    
    
    // 등록: body에 jrnDate, dcType, acctCode, amount 등 포함
    // Mapper XML의 <selectKey> 로 FN_NEXT_JRN_NO 호출되어 jrnCode 자동 채움
    @Transactional
    @PostMapping("/journal")
    public ResponseEntity<JournalVO> registerJournal(@RequestBody JournalVO vo) {
        int cnt = journalService.insertJournal(vo);
        return (cnt > 0) ? ResponseEntity.ok(vo) : ResponseEntity.badRequest().build();
    }
    
    
    // 수정(경로 변수로 키 받기) — 프론트에서 /{jrnNo}/{lineNo} 쓰는 패턴 대응
    @Transactional
    @PutMapping("/journal/{jrnNo}/{lineNo}")
    public int modifyJournalByPath(@PathVariable String jrnNo,
                                   @PathVariable Long lineNo,
                                   @RequestBody JournalVO vo) {
        vo.setJrnNo(jrnNo);
        vo.setLineNo(lineNo);
        return journalService.updateJournal(vo);
    }
	
	
	
}
