package com.yedam.erp.mapper.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.PaymentApplyVO;
import com.yedam.erp.vo.account.PaymentHeaderVO;

@Mapper
public interface PaymentMapper {

    // 미결(잔액 남은) 전표 조회
    List<InvoiceHeaderVO> selectUnpaidInvoices(
        @Param("type") String type,
        @Param("fromDate") String fromDate,
        @Param("toDate") String toDate,
        @Param("searchCus") String searchCus,
        @Param("companyCode") Long companyCode
    );

    
    //  자금전표 헤더 등록
    void insertPaymentHeader(PaymentHeaderVO vo);

    //  현재 시퀀스 값 (방금 insert된 PAY_CODE)
    Long selectCurrPayCode();

    //  자금전표 상세 등록
    void insertPaymentApply(PaymentApplyVO vo);

    // 미결금액 차감
    void updateInvoiceUnpaid(PaymentApplyVO vo);
    
    // ✅ 일반전표 등록 후 상태 업데이트
    int updatePostedFlag(Map<String, Object> param);
    // 조회
    List<PaymentHeaderVO> getPaymentList(Long companyCode);
}