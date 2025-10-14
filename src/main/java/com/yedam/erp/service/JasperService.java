package com.yedam.erp.service;

import java.math.BigDecimal;
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
	 
	 public static String convertToKorean(BigDecimal num) {
		    String[] han1 = {"", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		    String[] han2 = {"", "십", "백", "천"};
		    String[] han3 = {"", "만", "억", "조"};

		    System.out.println(num);
		    
		    
		    StringBuilder result = new StringBuilder();
		    String numStr = String.valueOf(num);
		    System.out.println(numStr);
		    int len = numStr.length();
		    //System.out.println(len);
		    int groupCount = 0;

		    while (len > 0) {
		        int start = Math.max(len - 4, 0);
		        String group = numStr.substring(start, len);
		        StringBuilder groupKor = new StringBuilder();
		        int groupLen = group.length();

		        for (int i = 0; i < groupLen; i++) {
		            int n = group.charAt(i) - '0';
		            if (n != 0) {
		                groupKor.append(han1[n]).append(han2[groupLen - i - 1]);
		            }
		        }

		        if (groupKor.length() > 0) {
		            groupKor.append(han3[groupCount]);
		            result.insert(0, groupKor);
		        }

		        groupCount++;
		        len -= 4;
		    }

		    result.append("원");
		    return result.toString();
		}
	 
	 public static String convertToKoreanV2(BigDecimal num) {
		    String[] han1 = {"", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		    String[] han2 = {"", "십", "백", "천"};
		    String[] han3 = {"", "만", "억", "조"};

		    // BigDecimal → 문자열, 정수/소수 분리
		    String numStr = num.toPlainString();
		    String[] parts = numStr.split("\\.");
		    String integerPart = parts[0]; // 정수 부분
		    String decimalPart = parts.length > 1 ? parts[1] : ""; // 소수 부분

		    StringBuilder result = new StringBuilder();

		    // 정수 부분 처리
		    int len = integerPart.length();
		    int groupCount = 0;

		    while (len > 0) {
		        int start = Math.max(len - 4, 0);
		        String group = integerPart.substring(start, len);
		        StringBuilder groupKor = new StringBuilder();
		        int groupLen = group.length();

		        for (int i = 0; i < groupLen; i++) {
		            int n = group.charAt(i) - '0';
		            if (n != 0) {
		                groupKor.append(han1[n]).append(han2[groupLen - i - 1]);
		            }
		        }

		        if (groupKor.length() > 0) {
		            groupKor.append(han3[groupCount]);
		            result.insert(0, groupKor);
		        }

		        groupCount++;
		        len -= 4;
		    }

		    result.append("원");

		    // 소수점 처리 (있으면 "점 ~" 형식)
		    if (!decimalPart.isEmpty()) {
		        result.append(" ").append("점");
		        for (char c : decimalPart.toCharArray()) {
		            int n = c - '0';
		            if (n >= 0 && n <= 9) {
		                result.append(han1[n]);
		            }
		        }
		    }

		    return result.toString();
		}
	 
}
