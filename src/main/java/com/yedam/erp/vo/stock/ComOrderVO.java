package com.yedam.erp.vo.stock;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ComOrderVO {

	private String doCode;
	private Long amount;
	private String cusCode;
	private String cusName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date dueDate;
	private String productName;
	
	
	
}
