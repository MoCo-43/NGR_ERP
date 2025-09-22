package com.yedam.erp.vo.stock;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductVO {

	
	private String productCode;
	private String productName;
	private String specification;
	private String empName;
	private MultipartFile productImage;
	private String productImageName;
	private int purchasePrice;
	private int salesPrice;
	private String note;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date insertDate;
	private int leadTime;
	
}
