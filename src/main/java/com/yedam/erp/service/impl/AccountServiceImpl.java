package com.yedam.erp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.erp.mapper.account.accountMapper;
import com.yedam.erp.service.AccountService;
import com.yedam.erp.vo.account.accountVO;
import com.yedam.erp.vo.account.invoiceVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	final accountMapper accmapper;
	
	
	// 계정과목 리스트
	@Override
	public List<accountVO> accountList(String category) {
		return accmapper.selectAll(category);
	}

	
	// 계정과목 사용토클
	@Override
	@Transactional
	public int updateYN(String acctCode) {
		return accmapper.updateYN(acctCode);
	}

	

	@Override
	public List<accountVO> parseExcel(MultipartFile file) throws Exception {
	    List<accountVO> list = new ArrayList<>();
	    try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	        Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 첫 번째 row는 헤더라 가정
	            Row row = sheet.getRow(i);
	            if (row == null) continue;

	            accountVO vo = new accountVO();
	            vo.setAcctCode(row.getCell(0).getStringCellValue());
	            vo.setAcctName(row.getCell(1).getStringCellValue());
	            vo.setStdAcctName(row.getCell(2).getStringCellValue());
	            vo.setCategory(row.getCell(3).getStringCellValue());
	            vo.setUseYn("Y");
	            list.add(vo);
	        }
	    }
	    return list;
	}

	@Override
	public int bulkInsert(List<accountVO> accounts) {
		    return accmapper.bulkInsert(accounts);
	
	}

	// 매출 매입전표 출력
	@Override
	public List<invoiceVO> selectInvoice() {
		return accmapper.selectInvoice();
	}

}
