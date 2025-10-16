package com.yedam.erp.vo.stock;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class InvenVO {
  
	private String icCode;
	private String empId;
	private String icStatus;
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date icDate;
	private Long companyCode;
	private String finalSign;
	private String empSign;
	
	
	
	
	private List<InvenDetailVO> detail;
	
}
