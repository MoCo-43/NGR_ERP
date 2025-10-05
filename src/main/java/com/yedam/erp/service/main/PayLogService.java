package com.yedam.erp.service.main;

import java.util.List;

import com.yedam.erp.vo.main.PayLogVO;

public interface PayLogService {
    List<PayLogVO> payLogList(String subCode);

}
