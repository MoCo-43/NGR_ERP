package com.yedam.erp.web.ApiController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.CommonCodeService;
import com.yedam.erp.vo.common.CommonCodeVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common-codes")
public class CommonCodeApiController {

    private final CommonCodeService service;

   
    @GetMapping
    public List<CommonCodeVO> list(
        @RequestParam("groupId") String groupId,
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "useYn", required = false) String useYn
    ) {
        return service.listCommonCodes(groupId, keyword, useYn);
    }

    
    @GetMapping("/one")
    public CommonCodeVO one(
        @RequestParam("groupId") String groupId,
        @RequestParam("codeId") String codeId
    ) {
        return service.getCommonCode(groupId, codeId);
    }
}
