package com.yedam.erp.web.ApiController;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.VoucherService;
import com.yedam.erp.vo.VoucherVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vouchers")
public class VoucherController {

    private final VoucherService service;
    
    // ----------- 전체 조회 -----------
    @GetMapping
    public List<VoucherVO> list(@RequestParam(required = false) String type,
                                @RequestParam(required = false) String status,
                                @RequestParam(required = false) String keyword) {
        return service.list(type, status, keyword);
    }

    // ----------- 단건 조회 (의문)-----------
    @GetMapping("/{id}")
    public VoucherVO get(@PathVariable Long id) {
        return service.get(id);
    }

    // ----------- 등록 -----------
    @PostMapping
    public Long create(@RequestBody VoucherVO vo) {
        return service.create(vo);
    }

    // ----------- 수정 -----------
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody VoucherVO vo) {
        service.update(id, vo);
    }

    // ----------- 삭제 -----------
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}