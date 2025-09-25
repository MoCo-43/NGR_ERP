package com.yedam.erp.vo.main;

import java.util.Date;

import lombok.Data;

@Data
public class Documents {
	private Long docNo;
	private String docName;
	private String filePath;
	private Date createAt;
	private Long matNo;
}
