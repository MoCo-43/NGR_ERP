package com.yedam.erp.service.hr;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfReportConfiguration;

@Service
@RequiredArgsConstructor
public class PayrollReportService {

	private final DataSource dataSource; // JDBC로 리포트 쿼리 돌릴 때 사용

	public JasperPrint buildPayrollPrint(Long payrollNo, String companyName, String yearMonth, // "2025-09"
			String deptName, Long companyCode) throws Exception {

		// 1)서 JRXML 열기 (경로 주의!)
		try (InputStream is = getClass().getResourceAsStream("/reports/payroll_report.jrxml");
				Connection conn = dataSource.getConnection()) {

			if (is == null) {
				throw new IllegalStateException("JRXML not found: /reports/payroll_report.jrxml");
			}

			// 2) 컴파일
			JasperReport jasper = JasperCompileManager.compileReport(is);

			// 3) 파라미터 매핑 
			Map<String, Object> params = new HashMap<>();
			params.put("PAYROLL_NO", payrollNo);
			params.put("P_company", companyName);
			params.put("p_year_month", yearMonth);
			params.put("p_dept", deptName);
			params.put("P_companyCode", companyCode);

			// 4) 채우기 (JDBC 커넥션)
			return JasperFillManager.fillReport(jasper, params, conn);
		}
	}

	/** JasperPrint → PDF 바이트 배열 */
	public byte[] exportPdf(JasperPrint print) throws JRException {
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(print));

		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

		SimplePdfReportConfiguration cfg = new SimplePdfReportConfiguration();
		cfg.setSizePageToContent(true); // 페이지 사이즈 자동 맞춤(선택)
		exporter.setConfiguration(cfg);

		exporter.exportReport();
		return baos.toByteArray();
	}
}
