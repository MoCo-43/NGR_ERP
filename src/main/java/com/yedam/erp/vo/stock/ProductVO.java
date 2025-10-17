package com.yedam.erp.vo.stock;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ProductVO {

	
	private String productCode;
	private String productName;
	private String specification;
	private String empName;
	private MultipartFile productImage;
	private String productImageName;
	@NumberFormat(pattern = "#,###")
	private int purchasePrice;
	@NumberFormat(pattern = "#,###")
	private int salesPrice;
	private String note;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
	private Date insertDate;
	private int leadTime;
	private Long companyCode;
	private String vatType;
	
}
