package com.yedam.erp.web.ApiController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.JasperService;
import com.yedam.erp.service.stock.StockService;
import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.stock.ComOrderDetailVO;
import com.yedam.erp.vo.stock.ComOrderVO;
import com.yedam.erp.vo.stock.InboundVO;
import com.yedam.erp.vo.stock.InvenDetailVO;
import com.yedam.erp.vo.stock.InvenVO;
import com.yedam.erp.vo.stock.OrderDetailVO;
import com.yedam.erp.vo.stock.OrderPlanVO;
import com.yedam.erp.vo.stock.OrderVO;
import com.yedam.erp.vo.stock.OutboundHeaderVO;
import com.yedam.erp.vo.stock.OutboundVO;
import com.yedam.erp.vo.stock.PartnerVO;
import com.yedam.erp.vo.stock.ProductVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
public class StockController {

	final private StockService service;
	final private JasperService jasper;
	
	@Autowired
	DataSource datasource;
	
	@Value("${file.upload.dir}")
    private String uploadDir;   // application.properties 경로 읽기
	
//	@Value("${yourcloud.upload.dir}")
//	private String signDir;
	
	// 세션 회사코드 불러오기
	@GetMapping("/getCompId")
	public Long getCompId(Model model) {
		 Long compId = SessionUtil.companyId();
	        return compId != null ? compId : 0L; // null 방지
	}
	
	
	// 세션 사원이름 불러오기
	@GetMapping("/getEmpName")
	public String getEmpName(Model model) {
		 String getSessionName = SessionUtil.empName();
		 return getSessionName;
		 // String empName = SessionUtil.empId();
		// return empName;
	}
	
	// 결산 생성
	@PostMapping("/createICData")
	public ResponseEntity<?> createICData() {
		String empId = SessionUtil.empId();
		Long compCode = SessionUtil.companyId();
		service.insertInvenClosing(empId , compCode);
		return ResponseEntity.ok("생성완료");
	}
	
	@PostMapping("/icFilteredList") // 결산 필터링
	public List<InvenVO> icFilteredList(@RequestBody Map<String,Object> params){
		// 전체 선택 시 빈 문자열("")이면 null로 변경
	    Object status = params.get("icStatus");
	    if (status instanceof String && ((String) status).trim().isEmpty()) {
	        params.put("icStatus", null);
	    }
		return service.findInvenList(params);
	}
	
	
	@GetMapping("/icList") // 결산 리스트
	public List<InvenVO> getIcList(){
		Long companyCode = SessionUtil.companyId();
		// System.out.println(companyCode);
		return service.getIcList(companyCode);
	}
	
	@PostMapping("/icList") // 결산 리스트
	public List<InvenVO> getIcList(@RequestBody Map<String , Object>params){
		Long companyCode = SessionUtil.companyId();
		// System.out.println(companyCode);
		params.put("companyCode", companyCode);
		return service.getIcListByParam(params);
	}
	
	
	@GetMapping("/icDetailList/{selectedRow}") // 결산 상세 리스트
	public List<InvenDetailVO> getIcDetailList(@PathVariable String selectedRow){
		Long companyCode = SessionUtil.companyId();
		// System.out.println(companyCode);
		return service.getIcDetailList(companyCode,selectedRow);
	}
	
	@GetMapping("/inboundList") // 입고 조회
	public List<InboundVO> getInboundList(){
		Long companyCode = SessionUtil.companyId();
		// System.out.println(companyCode);
		return service.getInboundList(companyCode);
	}
	
	@PostMapping("/inboundList") // 입고 필터링 조회
	public List<InboundVO> getInboundList(@RequestBody Map<String,Object> params) {
		
	    return service.findInboundList(params);
	}
	
	
	@GetMapping("/inDetailList/{selectedRow}")
	public List<InboundVO> getInDetail(@PathVariable String selectedRow) {
		return service.getInboundDetail(selectedRow);
	}
	
	
	@GetMapping("/orderDetail/{orderCode}")
	public List<OrderDetailVO> getOrderDetailList(@PathVariable String orderCode){
		return service.getOrderDetailByXpCode(orderCode);
	}
	
	
	@PostMapping("/inboundInsert")
	public ResponseEntity<?> insertInbound(@RequestBody Map<String, List<InboundVO>> payload) {
	    List<InboundVO> details = payload.get("details"); // JS에서 보낸 배열
	    service.insertInbound(details);
	    return ResponseEntity.ok("등록 성공");
	}
	
	
	@GetMapping("/delOrderList")
	public List<ComOrderVO> getDeliveryOrderList(){
		return service.getDeliveryOrderList();
	}
	
	
	@GetMapping("/deliveryOrderDetail/{doCode}")
	public List<ComOrderDetailVO> getDeliOrderDetailList(@PathVariable String doCode){
		return service.getComOrderDetailList(doCode);
	}
	
	
	@PostMapping("/outboundInsert")
	public ResponseEntity<?> insertOutbound(@RequestBody OutboundHeaderVO payload){
		// System.out.println("넘어온 payload : "+payload);
//		if (payload.toString().isEmpty()) {
		// NULL || empty CHECK
//			System.out.println("payload의 값이 비어있습니다.\n다시 확인해주세요.");
//		}
		try {
			service.insertOutbound(payload);
			return ResponseEntity.ok("출고등록 완료!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("등록 실패: " + e.getMessage());
		}
	}
	
	
	@GetMapping("/outboundList")
	public List<OutboundHeaderVO> getOutboundList(){
		return service.getOutboundList();
	}
	
	
	@GetMapping("/outboundList/{selectedRow}/{doCode}")
	public List<OutboundVO> getOutboundDetailList(@PathVariable String selectedRow , @PathVariable String doCode){
		return service.selectOutboundByOutbHeaderCode(selectedRow, doCode);
	}
	
	
	// 출고조회 - 필터
	@PostMapping("/outboundList/search")
	public List<OutboundVO> filteredOutboundList(@RequestBody Map<String , Object> params){
		return service.filteredOutboundList(params);
	}
	
	
	@GetMapping("/outboundSheet/{outbHeaderCode}/{businessCode}/{doCode}") // 발주 조회시 발주서 상세 조회()
	public void outboundListReport(@PathVariable String outbHeaderCode,@PathVariable String businessCode , @PathVariable String doCode ,HttpServletRequest request, HttpServletResponse response) throws Exception { 
	 //Connection conn = datasource.getConnection();
	 // 소스 컴파일 jrxml -> jasper
	 //InputStream stream = getClass().getResourceAsStream("/reports/deliveryNoteSheet.jrxml");// jrxml 용
	 InputStream stream = getClass().getResourceAsStream("/reports/deliveryNoteSheet.jasper"); // jasper 용
	 //JasperReport jasperReport = JasperCompileManager.compileReport(stream); // jrxml 용
	 JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream); // jasper 용
	 
	 // 상세 조회
	 List<OutboundVO> detailList = service.selectOutboundByOutbHeaderCode(outbHeaderCode, doCode);
	 
	 //데이터 조회
	 JRDataSource jRdataSource = new JRBeanCollectionDataSource(detailList);
	 
	 // 세션 회사 정보 조회
	 CompanyVO comp = service.selectComp(SessionUtil.companyId());
	 
	 // 발주서 발주넣을 거래처 정보 조회
	 CustomerVO cust = service.selectCutomer(businessCode);
	 
	 //파라미터 맵
	 HashMap<String,Object> map = new HashMap<>(); 
	 map.put("outbHeaderCodeParam", outbHeaderCode); // 출고코드
	 map.put("doCodeParam", doCode); // 지시코드
	 
	 map.put("brmParam",comp.getBrm()); // 회사 사업자번호
	 map.put("compNameCeoParam", comp.getCompName()+"/"+comp.getCeo()); // 회사명 / 대표명
	 map.put("compAddrParam",comp.getCompAddr()); // 회사 주소
	 map.put("compMatParam",comp.getMatName()+"/"+comp.getMatTel()); // 회사담당자 / 담당자회선번호
	 
	 map.put("cusNameParam",cust.getCusName()); // 거래명세서 구매처 회사명
	 map.put("telParam",cust.getTel()); // 거래명세서 구매처 전화번호
	 map.put("emailParam",cust.getEmail()); // 거래명세서 구매처 이메일
     
     // 조회건 반환
	 response.setContentType("application/pdf");
	 response.setHeader("Content-Disposition", "inline; filename=deliveryNoteSheet.pdf");
     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, jRdataSource);
     //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
	 JasperExportManager.exportReportToPdfStream( jasperPrint, response.getOutputStream());
	}
	
	
	@GetMapping("/orderListSheet/{orderCode}/{businessCode}") // 발주 조회시 발주서 상세 조회()
	public void orderListReport(@PathVariable String orderCode , @PathVariable String businessCode , HttpServletRequest request, HttpServletResponse response) throws Exception { 
	 //Connection conn = datasource.getConnection();
	 // 소스 컴파일 jrxml -> jasper
	 //InputStream stream = getClass().getResourceAsStream("/reports/orderListSheet.jrxml");//jrxml 용
	 InputStream stream = getClass().getResourceAsStream("/reports/orderListSheet.jasper"); // jasper 용
	 //JasperReport jasperReport = JasperCompileManager.compileReport(stream); // jrxml 용
	 JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream); // jasper 용
	 
	 // 상세 조회
	 List<OrderDetailVO> detailList = service.getOrderDetailByOrderCode(orderCode);
	 
	 //데이터 조회
	 JRDataSource jRdataSource = new JRBeanCollectionDataSource(detailList);
	 
	 // 세션 회사 정보 조회
	 CompanyVO comp = service.selectComp(SessionUtil.companyId());
	 
	 // 발주서 발주넣을 거래처 정보 조회
	 CustomerVO cust = service.selectCutomer(businessCode);
	 
	 //파라미터 맵
	 HashMap<String,Object> map = new HashMap<>(); 
	 map.put("orderCodeParam", orderCode); // 발주코드
	 //map.put("companyId", SessionUtil.companyId()); // 세션 회사 ID
	 map.put("brnParam",comp.getBrm()); // 회사 사업자번호
	 map.put("compNameCeoParam", comp.getCompName()+"/"+comp.getCeo()); // 회사명 / 대표명
	 map.put("compAddrParam",comp.getCompAddr()); // 회사 주소
	 map.put("compMatParam",comp.getMatName()+"/"+comp.getMatTel()); // 회사담당자 / 담당자회선번호
	 
	 map.put("cusNameParam",cust.getCusName()); // 발주넣을 구매처 회사명
	 map.put("telParam",cust.getTel()); // 발주넣을 구매처 전화번호
	 map.put("emailParam",cust.getEmail()); // 발주넣을 구매처 이메일
     
     // 조회건 반환
	 response.setContentType("application/pdf");
	 response.setHeader("Content-Disposition", "inline; filename=orderListSheet.pdf");
     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, jRdataSource);
     //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
	 JasperExportManager.exportReportToPdfStream( jasperPrint, response.getOutputStream());
	}
	
	
	@GetMapping("/orderSheet/{xpCode}/{businessCode}") // 발주 계획 조회시 발주서 조회(발주계획기준 등록시)
	public void report(@PathVariable String xpCode ,@PathVariable String businessCode, HttpServletRequest request, HttpServletResponse response) throws Exception { 
	 //Connection conn = datasource.getConnection();
	 // 소스 컴파일 jrxml -> jasper
	 // InputStream stream = getClass().getResourceAsStream("/reports/orderSheet.jrxml"); // jrxml 용
	 // JasperReport jasperReport = JasperCompileManager.compileReport(stream); // jrxml 용
	 // ✅ .jasper 파일 로드
	 InputStream stream = getClass().getResourceAsStream("/reports/orderSheet.jasper"); // jasper 용
	 JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream); // jasper 용
	 
	 // 상세 조회
	 List<OrderDetailVO> detailList = service.selectOrderDetailsByXpCode(xpCode);
	 
	 //데이터 조회
	 JRDataSource jRdataSource = new JRBeanCollectionDataSource(detailList);
	 
	 // 세션 회사 정보 조회
	 CompanyVO comp = service.selectComp(SessionUtil.companyId());
	 
	 // 발주서 발주넣을 거래처 정보 조회
	 CustomerVO cust = service.selectCutomer(businessCode);
	 
	 //파라미터 맵
	 HashMap<String,Object> map = new HashMap<>(); 
	 map.put("xpCodeParam", xpCode); // 발주계획코드
	 //map.put("companyId", SessionUtil.companyId()); // 세션 회사 ID
	 map.put("brnParam",comp.getBrm()); // 회사 사업자번호
	 map.put("compNameCeoParam", comp.getCompName()+"/"+comp.getCeo()); // 회사명 / 대표명
	 map.put("compAddrParam",comp.getCompAddr()); // 회사 주소
	 map.put("compMatParam",comp.getMatName()+"/"+comp.getMatTel()); // 회사담당자 / 담당자회선번호
	 
	 map.put("cusNameParam",cust.getCusName()); // 발주넣을 구매처 회사명
	 map.put("telParam",cust.getTel()); // 발주넣을 구매처 전화번호
	 map.put("emailParam",cust.getEmail()); // 발주넣을 구매처 이메일
     
     // 조회건 반환
	 response.setContentType("application/pdf");
	 response.setHeader("Content-Disposition", "inline; filename=orderSheet.pdf");
     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, jRdataSource);
     //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
	 JasperExportManager.exportReportToPdfStream( jasperPrint, response.getOutputStream());
	}
	
	@GetMapping("/orderList") // 발주 조회
	public List<OrderVO> getOrderList(){
		Long compId = SessionUtil.companyId();
		return service.getOrderList(compId);
	}
	
	@PostMapping("/orderList/search") // 발주 조회
	public List<OrderVO> getFilterdOrderList(@RequestBody Map<String, Object> params){
		System.out.println("sysout >> params : "+params.get("dueDate"));
		return service.getFilteredOrderList(params);
	}

	@PostMapping("/orderInsert") // 발주 등록
	public ResponseEntity<String> insertOrderReq(@RequestBody  OrderVO order){
		//service.insertOrderReq(order);
		try {
			// advice - error 메세지관리자도 따로 만들수 있으니 되도록 스프링부트에서는 
			//                try ~ catch 사용을 지양하자
	        service.insertOrderReq(order);
	        return ResponseEntity.ok("등록 성공");
	    } catch(Exception e) {
	    	e.printStackTrace();// 스택을 찍어주지 않아서 콘솔창에 에러메세지가 나오지 않음
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("등록 실패: " + e.getMessage());
	    }
	}
	
	
	@GetMapping("/orderPlans") // 발주 계획 조회
	public List<OrderPlanVO> getOrderPlans() {
        return service.getOrderPlans();
    }
	
	@PostMapping("/orderPlans")
	public List<OrderPlanVO> filteredOrderPlans(@RequestBody Map<String , Object> params){
		return service.filteredOrderPlans(params);
	}
	
	@PostMapping("/orderPlanInsert") // 발주 계획 등록
	public ResponseEntity<String> insertOrderPlan(@RequestBody OrderPlanVO plan) {
		 service.insertOrderPlan(plan);
		 return ResponseEntity.ok("등록 성공");
	}
	
	@PostMapping("/filteredProductList")
	public List<ProductVO> filteredProductList(@RequestBody Map<String, Object> params){
		return service.filteredProductList(params);
	}
	
	@GetMapping("/productList/{compCode}") // 제품리스트 모달
	public List<ProductVO> productList(@PathVariable Long compCode){
		return service.productAll(compCode);
	}
	
	@PostMapping("/product/search") // 제품리스트 필터조회 - 모달
	public List<ProductVO> filterdProductList(@RequestBody Map<String,Object> params){
		System.out.println("params.companyCode : "+params.get("companyCode"));
		System.out.println("params.productName : "+params.get("productName"));
		System.out.println("params.empName : "+params.get("empName"));
		System.out.println("params.note : "+params.get("note"));
		return service.filteredProductList(params);
	}
	
	@GetMapping("/selectProductImg/{compCode}/{productCode}")
	public ResponseEntity<Resource> getProductImg(@PathVariable Long compCode , @PathVariable String productCode){
		String fileName = service.getProductFileNameByProductCodeAndCompCode(compCode,productCode);
		
		// System.out.println("fileName : "+fileName);
		
		File file = new File(uploadDir, fileName);
		// System.out.println("file route : "+file);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 2️⃣ 확장자 제거 후 미리보기용 이름 처리
        String displayName = fileName;
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            displayName = fileName.substring(0, dotIndex); // aaasss 형태로 표시
        }
        System.out.println("미리보기용 이름: " + displayName);
        
        
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
	
	@GetMapping("/selectProductDetail/{compCode}/{productCode}")
	public List<InboundVO> getProductDetailRefInbound(@PathVariable Long compCode , @PathVariable String productCode){
		return service.getProductDetailRefInbound(compCode , productCode);
	}
	
	
	 @ResponseBody
	 @GetMapping("/cusList/{compCode}") // 거래처리스트 모달
	 public List<PartnerVO> cutList(@PathVariable Long compCode){
		 return service.customerAll(compCode);
	 }
	 
	 @PostMapping("/filteredCustomerList")
	 public List<PartnerVO> filteredCustomerList(@RequestBody Map<String , Object> params){
		 return service.filteredCustomerList(params);
	 }
	
	 @PutMapping("/signUpdate") // 전자서명 등록 수정
	 public ResponseEntity<String> updateSign(@RequestBody InvenVO payload) {
		 try {
			 // System.out.println("payload.getFinalSign() : "+payload.getFinalSign());
			 // System.out.println("payload.getEmpSign() : "+payload.getEmpSign());
			 payload.setCompanyCode(SessionUtil.companyId());
			 service.updateSign(payload);
			 return ResponseEntity.ok("서명 등록 완료!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서명 등록 실패: " + e.getMessage());
		}
		 
	 }
	 
	 
	 @PostMapping("/inboundTrend") // 제품조회 페이지 - 입고추이 
	 public List<InboundVO> inboundTrendChart(@RequestBody Map<String , Object> payload){
		 return service.inboundTrendChart(payload);
	 }
	 
	 @PostMapping("/getInAndOutCountOutput") // dashboard 용 최근 6개월 입/출고 건수 조회
	 public List<Map<String , Object>> getInAndOutCountOutput(@RequestBody Map<String , Object> params){
		 // System.out.println("Api-controller > compCode : "+params.get("compCode"));
		 return service.getInAndOutCountOutput(params);
	 }
	 
	
	 @PostMapping("/productInsert") // 제품등록
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
	            String newFileName = uuid + "." + ext;

	            // DB에 저장할 이름 설정
	            product.setProductImageName(newFileName);

	            // 서버에 저장
	            File dir = new File(uploadDir);
	            if (!dir.exists()) dir.mkdirs();
	            file.transferTo(new File(dir, newFileName));
	        }
	        product.setCompanyCode(SessionUtil.companyId());
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
	    
	    
	    @PostMapping("/uploadSign") // canvas 로 그려진 서명 등록
	    public ResponseEntity<String> uploadSign(@RequestParam("file") MultipartFile file) {
	    	try {
	            // ✅ 루트경로(/upload)에 sign 하위폴더 추가
	            String signDirPath = uploadDir + File.separator + "sign";

	            // ✅ sign 디렉토리 생성 (없으면 자동 생성)
	            File signDir = new File(signDirPath);
	            if (!signDir.exists()) {
	                boolean created = signDir.mkdirs();
	                if (!created) {
	                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                            .body("업로드 실패: sign 폴더 생성 실패 (" + signDirPath + ")");
	                }
	            }

	            // ✅ 파일 저장
	            String fileName = file.getOriginalFilename();
	            Path path = Paths.get(signDirPath, fileName);
	            Files.write(path, file.getBytes());

	            return ResponseEntity.ok("업로드 성공: " + path.toString());

	        } catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("업로드 실패: " + e.getMessage());
	        }
			/*
			 * try { // ✅ OS 자동 감지 String osName =
			 * System.getProperty("os.name").toLowerCase();
			 * 
			 * // ✅ 환경별 업로드 경로 지정 String baseUploadDir; if (osName.contains("win")) {
			 * baseUploadDir = "C:\\upload"; // 로컬 (Windows) } else { baseUploadDir =
			 * "/upload"; // 리눅스 (운영) }
			 * 
			 * // ✅ sign 폴더 생성 (없을 시 자동 생성) String signDirPath = baseUploadDir +
			 * File.separator + "sign"; File signDir = new File(signDirPath); if
			 * (!signDir.exists() && !signDir.mkdirs()) { return
			 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			 * .body("업로드 실패: sign 폴더 생성 실패 (" + signDirPath + ")"); }
			 * 
			 * // ✅ 파일 저장 String fileName = file.getOriginalFilename(); Path path =
			 * Paths.get(signDirPath, fileName); Files.write(path, file.getBytes());
			 * 
			 * return ResponseEntity.ok("업로드 성공: " + path); } catch (IOException e) { return
			 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body("업로드 실패: " +
			 * e.getMessage()); }
			 */
	    }

	    
	    // 연간 주문건수 대시보드 조회
	    @GetMapping("/getAnnualOrderCnt")
	    public List<Map<String, Object>>getAnnualOrderCnt(){
	    	Long companyCode = SessionUtil.companyId();
	    	return service.getAnnualOrderCnt(companyCode);
	    }
	 
	    
	    // 연간 주문건수 대시보드 상세 조회 - 기본
	    @GetMapping("/getAnnualOrderDetail")
	    public List<Map<String , Object>> getAnnualOrderDetail(){
	    	Long companyCode = SessionUtil.companyId();
	    	return service.getAnnualOrderDetail(companyCode);
	    }
	    
	    
	    // 연간 주문건수 대시보드 상세 조회 - 키워드 필터
	    @PostMapping("/getAnnualOrderDetailByParam")
	    public List<Map<String , Object>> getAnnualOrderDetailByParam(@RequestBody Map<String , Object> params){
	    	 params.put("companyCode", SessionUtil.companyId());
	    	return service.getAnnualOrderDetailByParam(params);
	    }
	
}
