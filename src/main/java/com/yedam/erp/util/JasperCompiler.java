package com.yedam.erp.util;

import net.sf.jasperreports.engine.JasperCompileManager;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 🔧 Jasper 리포트 자동 컴파일러
 * -------------------------------------------
 * ✅ 역할: .jrxml → .jasper 변환 (운영환경 배포용)
 * ✅ 실행: 로컬에서 1회 실행
 * ✅ 환경: Windows / Linux 동일 경로 자동 인식
 *
 * 예) mvn exec:java -Dexec.mainClass="com.yedam.erp.util.JasperCompiler"
 */
public class JasperCompiler {
	public static void main(String[] args) throws Exception {

	  String basePath = "src/main/resources/reports"; // 로컬 개발용 기본 경로

      // 리눅스/윈도우 호환 경로
      String[] reports = {
          basePath + "/orderSheet.jrxml",
          basePath + "/orderListSheet.jrxml",
          basePath + "/deliveryNoteSheet.jrxml"
      };

      for (String jrxml : reports) {
          File file = new File(jrxml);
          if (!file.exists()) {
              System.err.println("❌ 파일 없음: " + file.getAbsolutePath());
              continue;
          }

          String jasper = jrxml.replace(".jrxml", ".jasper");
          JasperCompileManager.compileReportToFile(jrxml, jasper);
          System.out.println("✅ Compiled: " + jasper);
      }
	}

	/*
	 * // 리포트 경로 (리소스 폴더) private static final String REPORT_DIR =
	 * "src/main/resources/reports";
	 * 
	 * public static void main(String[] args) { try { Path reportPath =
	 * Paths.get(REPORT_DIR); if (!Files.exists(reportPath)) {
	 * System.err.println("❌ 리포트 폴더를 찾을 수 없습니다: " + reportPath.toAbsolutePath());
	 * return; }
	 * 
	 * // 폴더 내 모든 jrxml 파일 탐색 Files.walk(reportPath) .filter(p ->
	 * p.toString().endsWith(".jrxml")) .forEach(JasperCompiler::compileFile);
	 * 
	 * System.out.println("\n✅ 모든 Jasper 파일이 성공적으로 컴파일되었습니다!"); } catch (Exception
	 * e) { System.err.println("❌ 컴파일 중 오류 발생: " + e.getMessage());
	 * e.printStackTrace(); } }
	 * 
	 * private static void compileFile(Path jrxmlPath) { try { String jasperPath =
	 * jrxmlPath.toString().replace(".jrxml", ".jasper");
	 * 
	 * // .jasper가 이미 있으면 덮어쓰기 여부 출력 File jasperFile = new File(jasperPath); if
	 * (jasperFile.exists()) { System.out.println("🔁 기존 파일 덮어쓰기: " + jasperPath); }
	 * 
	 * // 실제 컴파일 JasperCompileManager.compileReportToFile(jrxmlPath.toString(),
	 * jasperPath); System.out.println("✅ Compiled: " + jasperPath);
	 * 
	 * } catch (Exception e) { System.err.println("❌ 실패: " + jrxmlPath + " (" +
	 * e.getMessage() + ")"); } }
	 */
}
