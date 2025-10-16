package com.yedam.erp.web.ApiController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.ApprovalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/approval")
public class ApprovalController {

    private final ApprovalService approvalService;
    
    @Value("${file.upload.dir}")
    private String baseDirectory;

//    @Value("${upload.sign.public-path}")
//    private String publicPath;


    // 공통 서명 이미지 업로드
    @PostMapping(value = "/signature", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadSignature(@RequestPart("file") MultipartFile file) {
        try {
            String baseDir =  baseDirectory + File.separator + "uploads" + File.separator + "signatures";
            Files.createDirectories(Path.of(baseDir));

            String original = StringUtils.hasText(file.getOriginalFilename()) ? file.getOriginalFilename() : "sign.png";
            String filename = System.currentTimeMillis() + "_" + original;
            Path savePath = Path.of(baseDir, filename);
            file.transferTo(savePath.toFile());

            String publicPath = "/uploads/signatures/" + filename;

            return ResponseEntity.ok(Map.of(
                "success", true,
                "path", publicPath
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "upload failed"));
        }
    }

    // 상태 변경 (공통)
    @PutMapping("/{docType}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String docType, @RequestBody Map<String, Object> body) {
        List<String> ids = (List<String>) body.get("ids");
        String status = (String) body.get("status");
        Long companyCode = SessionUtil.companyId();

        int updated = approvalService.updateStatus(docType, ids, status, companyCode);
        return ResponseEntity.ok(Map.of("updated", updated));
    }

    // 승인 처리 (공통)
    @PutMapping("/{docType}/approve")
    public ResponseEntity<?> approve(@PathVariable String docType, @RequestBody Map<String, Object> body) {
        List<String> ids = (List<String>) body.get("ids");
        String signPath = (String) body.get("signPath");

        Long companyCode = SessionUtil.companyId();
        String approver  = SessionUtil.empName();

        int updated = approvalService.approveWithSign(docType, ids, signPath, companyCode, approver);
        return ResponseEntity.ok(Map.of("updated", updated));
        
    }
}