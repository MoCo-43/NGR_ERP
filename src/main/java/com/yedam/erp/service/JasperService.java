package com.yedam.erp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class JasperService {
	// 재고파트 - 발주등록후 발주서 PDF 
	 public byte[] generatePdf(String xpCode) {
	        try {
	            // jasper 파일 위치
	            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(
	                    getClass().getResource("/reports/orderSheet.jasper")
	            );

	            Map<String, Object> params = new HashMap<>();
	            params.put("XP_CODE", xpCode);

	            // 빈 데이터 소스 (데이터가 없으면 JREmptyDataSource 사용 가능)
	            JRDataSource dataSource = new JREmptyDataSource();

	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

	            return JasperExportManager.exportReportToPdf(jasperPrint);

	        } catch (JRException e) {
	            e.printStackTrace();
	            return new byte[0];
	        }
	    }
}
