package com.yedam.erp.vo.account;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 일반전표 (journal) VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalVO {
	private String jrnCode;		//기본키
    private Long lineNo;          // 라인 번호
    private String jrnNo;           // 전표 번호
    private Date jrnDate;         // 전표일자
    private String acctCode;      // 계정코드
    private String dcType;        // 차/대 구분
    private Long amount;          // 금액
    private String remarks;       // 적요
    private String status;        // 상태
    private Date createdAt;       // 생성일자
    private String createdBy;     // 생성자
    private String cusCode;       // 거래처 코드
    private Long closeNo;         // 마감 번호
    private Long companyCode;
    
}
