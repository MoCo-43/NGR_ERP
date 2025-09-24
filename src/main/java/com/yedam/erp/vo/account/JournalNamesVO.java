package com.yedam.erp.vo.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class JournalNamesVO extends JournalVO {
	    private String acctName;   // 계정명
	    private String cusName;    // 거래처명
	}
