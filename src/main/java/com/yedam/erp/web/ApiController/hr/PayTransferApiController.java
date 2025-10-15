package com.yedam.erp.web.ApiController.hr;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.hr.PayTransferService;
import com.yedam.erp.vo.hr.PayTransferVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/pay-transfer")
public class PayTransferApiController {

    private final PayTransferService payTransferService;

    // 세션에서 회사코드 획득 (PayrollApiController 스타일)
    private long companyCode() {
        return Long.parseLong(SessionUtil.companyId().toString());
    }

    //확정건 실시간 조회
    @GetMapping("/view")
    public List<PayTransferVO> getTransferViewFromConfirmed(@RequestParam("payYm") String payYm) {
        return payTransferService.getTransferViewFromConfirmed(companyCode(), payYm);
    }
}
