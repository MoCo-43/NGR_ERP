package com.yedam.erp.vo.Biz;
import java.util.Date;

import lombok.Data;
@Data
public class ProductCodeVO {
    private String productCode;      // PRODUCT_CODE
    private String productName;      // PRODUCT_NAME
    private String specification;    // SPECIFICATION
    private String empName;          // EMP_NAME (등록자 or 담당자)
    private String productImage;     // PRODUCT_IMAGE (이미지 경로 or 파일명)
    private Long purchasePrice;      // PURCHASE_PRICE (매입가)
    private Long salesPrice;         // SALES_PRICE (판매가)
    private String note;             // NOTE (비고)
    private Date insertDate;    // INSERT_DATE (등록일자)
    private Integer leadTime;        // LEAD_TIME (소요기간 or 납기일)
    private Long companyCode;        // COMPANY_CODE (회사 코드)
    private String vatType;          // VAT_TYPE (과세유형: TAX, FREE, ZERO 등)
}