package com.yedam.erp.web.ApiController.hr;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.hr.PayAllowCodeService;
import com.yedam.erp.vo.hr.PayAllowCodeVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/allow-codes") 
public class PayAllowCodeApiController {

    private final PayAllowCodeService service;

    // 전체 조회 
    @GetMapping
    public List<PayAllowCodeVO> list() {
        Long companyCode = SessionUtil.companyId();
        return service.getAllowList(companyCode);
    }

    // 단건 조회
    @GetMapping("/{allowCode}")
    public PayAllowCodeVO detail(@PathVariable("allowCode") String allowCode) {
        PayAllowCodeVO param = new PayAllowCodeVO();
        param.setAllowCode(allowCode);
        return service.getAllow(param);
    }

    // 등록
    @PostMapping
    public int create(@RequestBody PayAllowCodeVO vo) {
        vo.setCompanyCode(SessionUtil.companyId());
        return service.addAllow(vo);
    }

    //  수정
    @PutMapping("/{allowCode}")
    public int update(@PathVariable("allowCode") String allowCode,
                      @RequestBody PayAllowCodeVO vo) {
        vo.setAllowCode(allowCode);
        vo.setCompanyCode(SessionUtil.companyId());
        return service.editAllow(vo);
    }
    //사용여부
    @PatchMapping("/{allowCode}/use-yn")
    public int changeUseYn(@PathVariable("allowCode") String allowCode,
                           @RequestParam("useYn") String useYn) {
        PayAllowCodeVO vo = new PayAllowCodeVO();
        vo.setAllowCode(allowCode);
        vo.setUseYn(useYn);
        vo.setCompanyCode(SessionUtil.companyId());
        return service.changeUseYn(vo);
    }
}
