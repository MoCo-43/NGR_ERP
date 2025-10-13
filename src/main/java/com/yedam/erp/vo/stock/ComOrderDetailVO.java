package com.yedam.erp.vo.stock;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ComOrderDetailVO {

	private String productCode;
	private String productName;
	private String specification;
	private Long amount;
	private String type;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date dueDate;
	
	
}
