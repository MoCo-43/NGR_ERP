package com.yedam.erp.web.ApiController.hr;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.hr.PayrollReportService;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/payroll") 
public class PayrollReportController {

	private final PayrollReportService payrollReportService;

	/** 급여대장 PDF 다운로드 */
	@GetMapping(value = "/report", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> downloadPayrollReport(@RequestParam Long payrollNo, @RequestParam String yearMonth,
			@RequestParam String deptName) throws Exception {

		Long companyCode = SessionUtil.companyId();

		JasperPrint print = payrollReportService.buildPayrollPrint(payrollNo, null, 
				yearMonth, deptName, companyCode);

		byte[] pdf = payrollReportService.exportPdf(print);

		String fileName = URLEncoder.encode("payroll_" + yearMonth + "_" + deptName + ".pdf", StandardCharsets.UTF_8);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + fileName)
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}
}
