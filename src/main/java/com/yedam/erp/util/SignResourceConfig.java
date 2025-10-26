package com.yedam.erp.util;



import java.io.File;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
public class SignResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name").toLowerCase();
        String basePath = os.contains("win") ? "C:/upload" : "/home/ec2-user/upload";

        // 폴더가 없으면 생성 (이게 없으면 리눅스에서 존재하지 않아 에러남)
        File dir = new File(basePath + "/sign");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        System.out.println("🖋 [SignResourceConfig] basePath = " + dir.getAbsolutePath());

        registry.addResourceHandler("/sign/**")
                .addResourceLocations("file:" + dir.getAbsolutePath() + "/");
    }
}
//@Configuration
//public class JasperCompiler { public static void main(String[] args) throws Exception 
//{ String basePath = "src/main/resources/reports"; 
//// 로컬 개발용 기본 경로 // 리눅스/윈도우 호환 경로 
//String[] reports = { basePath + "/orderSheet.jrxml", basePath + "/orderListSheet.jrxml", basePath + "/deliveryNoteSheet.jrxml" }; 
//   for (String jrxml : reports) { File file = new File(jrxml); 
//        if (!file.exists()) { System.err.println("❌ 파일 없음: " + file.getAbsolutePath()); continue; }
//           String jasper = jrxml.replace(".jrxml", ".jasper"); 
//              JasperCompileManager.compileReportToFile(jrxml, jasper); 
//                   System.out.println("✅ Compiled: " + jasper); 
//                   } 
//} /* * // 리포트 경로 (리소스 폴더) private static final String REPORT_DIR = * "src/main/resources/reports"; * * public static void main(String[] args) { try { Path reportPath = * Paths.get(REPORT_DIR); if (!Files.exists(reportPath)) { * System.err.println("❌ 리포트 폴더를 찾을 수 없습니다: " + reportPath.toAbsolutePath()); * return; } * * // 폴더 내 모든 jrxml 파일 탐색 Files.walk(reportPath) .filter(p -> * p.toString().endsWith(".jrxml")) .forEach(JasperCompiler::compileFile); * * System.out.println("\n✅ 모든 Jasper 파일이 성공적으로 컴파일되었습니다!"); } catch (Exception * e) { System.err.println("❌ 컴파일 중 오류 발생: " + e.getMessage()); * e.printStackTrace(); } } * * private static void compileFile(Path jrxmlPath) { try { String jasperPath = * jrxmlPath.toString().replace(".jrxml", ".jasper"); * * // .jasper가 이미 있으면 덮어쓰기 여부 출력 File jasperFile = new File(jasperPath); if * (jasperFile.exists()) { System.out.println("🔁 기존 파일 덮어쓰기: " + jasperPath); } * * // 실제 컴파일 JasperCompileManager.compileReportToFile(jrxmlPath.toString(), * jasperPath); System.out.println("✅ Compiled: " + jasperPath); * * } catch (Exception e) { System.err.println("❌ 실패: " + jrxmlPath + " (" + * e.getMessage() + ")"); } } */ }


