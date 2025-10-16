package com.yedam.erp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.stock.StockMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.vo.stock.InvenDetailVO;
import com.yedam.erp.vo.stock.InvenVO;

@Component
public class Scheduler {

	@Autowired StockMapper mapper;
	
	
	@Scheduled(cron = "0 0 9 L * ?") // 매월 마지막 날 9시 실행
	@Transactional
	public void autoCreateMonthlySettlement() {
		System.out.println("[결산생성] 월말 재고결산 데이터 자동 생성 시작");
		String empId = SessionUtil.empId();
		Long compCode = SessionUtil.companyId();
		System.out.println("empId , compCode check"+empId+", "+compCode);
		InvenVO instance = new InvenVO();
	    instance.setCompanyCode(compCode);
	    instance.setEmpId(empId);
        mapper.insertInvenClosing(instance);// 마스터 처리
        
        InvenDetailVO instDatail = new InvenDetailVO();
		instDatail.setCompanyCode(compCode);
		instDatail.setEmpId(empId);
		instDatail.setIcCode(instance.getIcCode());
		
		mapper.insertInvenClosingDetail(instDatail); // 상세 처리
        System.out.println("월말 재고결산 자동 생성 완료 ✅");
    }
	
}
