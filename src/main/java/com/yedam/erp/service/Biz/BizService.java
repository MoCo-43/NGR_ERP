package com.yedam.erp.service.Biz;

import java.util.List;
import com.yedam.erp.vo.Biz.PurchaseOrderVO;

public interface BizService {

  // 테스트 전체목록 조회
  List<PurchaseOrderVO> getAllPO();

  // 주문서 조회
  List<PurchaseOrderVO> selectPO();

  // 주문서 등록
  int insertPO(PurchaseOrderVO vo);
}
