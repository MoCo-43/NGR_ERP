package com.yedam.erp.web.ApiController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.erp.service.stock.StockService;
import com.yedam.erp.vo.stock.OrderPlanVO;
import com.yedam.erp.vo.stock.PartnerVO;
import com.yedam.erp.vo.stock.ProductVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
public class StockController {

	final private StockService service;
	
	@PostMapping("/orderPlanInsert")
	public ResponseEntity<String> insertOrderPlan(@RequestBody OrderPlanVO plan) {
		 service.insertOrderPlan(plan);
		 return ResponseEntity.ok("등록 성공");
	}
	
	@GetMapping("/productList")
	public List<ProductVO> productList(){
		return service.productAll();
	}
	
	 @GetMapping("/cusList")
	 public List<PartnerVO> cutList(){
		 return service.customerAll();
	 }
	
	 @Value("${file.upload.dir}")
	    private String uploadDir;   // application.properties 경로 읽기
	
	
	 @PostMapping("/productInsert")
	    public int insertProduct(@ModelAttribute ProductVO product) throws IOException {
	        MultipartFile file = product.getProductImage();
	        if (file != null && !file.isEmpty()) {
//	            // DB에는 이름만 저장
//	            product.setProductImageName(file.getOriginalFilename());
//
//	            // 서버에 실제 파일 저장
//	            File dir = new File(uploadDir);
//	            if (!dir.exists()) dir.mkdirs();
//	            file.transferTo(new File(dir, file.getOriginalFilename()));
	        	
	        	// UUID 생성 + 확장자 유지
	            String uuid = UUID.randomUUID().toString();
	            String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
	            String newFileName = uuid + "_" + ext;

	            // DB에 저장할 이름 설정
	            product.setProductImageName(newFileName);

	            // 서버에 저장
	            File dir = new File(uploadDir);
	            if (!dir.exists()) dir.mkdirs();
	            file.transferTo(new File(dir, newFileName));
	        }
	        return service.insertProduct(product);
	        
	    }
	 
	 
	  // 이미지 불러오기
	    @GetMapping("/productImage/{fileName}")
	    public ResponseEntity<Resource> getProductImage(@PathVariable String fileName) {
	        File file = new File(uploadDir, fileName);
	        if (!file.exists()) {
	            return ResponseEntity.notFound().build();
	        }

	        Resource resource = new FileSystemResource(file);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");

	        // 확장자에 따른 MediaType 지정
	        String lowerName = fileName.toLowerCase(); // 파일명 소문자 처리
	        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM; // 기본값
	        if (lowerName.endsWith(".png")) mediaType = MediaType.IMAGE_PNG; // png 파일 처리
	        else if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) mediaType = MediaType.IMAGE_JPEG; // jpg처리
	        else if (lowerName.endsWith(".gif")) mediaType = MediaType.IMAGE_GIF; // gif 처리
	        
	        
	        // 미디어 타입은 필요에 따라 변경 가능
	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentLength(file.length())
	                //.contentType(MediaType.IMAGE_JPEG)
	                .contentType(mediaType)
	                .body(resource);
	    }
	 
	
	
}
