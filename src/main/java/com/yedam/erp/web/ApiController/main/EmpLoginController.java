package com.yedam.erp.web.ApiController.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.main.EmpLoginService;
import com.yedam.erp.vo.hr.EmpVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmpLoginController {

//	private final EmpService empService; // 사원 정보 조회를 위한 서비스
	private final EmpLoginService empLoginService; // 로그인 계정 관리를 위한 서비스
    /**
     * 사원 목록을 조회합니다. (부서별 검색 기능 포함)
     * hrmanager.html의 '검색' 버튼이 이 API를 호출합니다.
     */
//    @GetMapping("/hrLists")
//    public List<EmpVO> getEmployees(@RequestParam(required = false) String deptCode) {
//        return empLoginService.findEmployeesByDept(deptCode);
//    }
    @GetMapping("/hrLists")
    public List<Map<String, Object>> getEmployees(
            // 1. deptName으로 수정
            @RequestParam(value = "deptName", required = false) String deptName,
            // 2. title 파라미터 추가
            @RequestParam(value = "title", required = false) String title) {
        
        // 3. 서비스 호출 시 두 파라미터 모두 전달
        List<EmpVO> empList = empLoginService.findEmployeesByDept(deptName, title);

        // (System.out.println 로그는 문제 없음)
        for (EmpVO emp : empList) {
            System.out.println("emp_id=" + emp.getEmp_id() + ", name=" + emp.getName() +",title="+emp.getTitle() 
                + ", dept_code=" + emp.getDept_code() + ", email=" + emp.getEmail() + ", dept_name=" + emp.getDept_name());
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (EmpVO emp : empList) {
            Map<String, Object> map = new HashMap<>();
            map.put("empId", emp.getEmp_id());
            map.put("name", emp.getName());
            map.put("title", emp.getTitle());
            map.put("deptName", emp.getDept_name()); // deptName이 정상적으로 매핑됨
            map.put("email", emp.getEmail());
            result.add(map);
        }

        System.out.println(result); // Map 변환 후 확인
        return result;
    }


    /**
     * 초기 비번 전송' 버튼을 처리합니다.
     * 사원 ID를 로그인 ID로 사용하여 계정을 활성화합니다.
     */
    @PostMapping("/activate-default")
    public ResponseEntity<String> activateDefaultLogins(@RequestBody List<String> empIds) {
        // 컨트롤러는 "기본 ID로 활성화 해줘" 라는 요청만 전달합니다.
        empIds.forEach(empLoginService::activateDefaultLogin);
        return ResponseEntity.ok("선택된 사원들의 계정을 [사번]으로 활성화하고, 초기 비밀번호를 발송했습니다.");
    }
//    @PostMapping("/subscription/cancel")
//    public ResponseEntity<?> cancelSubscription(@RequestBody CancelRequest req) {
//        SubscriptionVO sub = subscriptionService.getSubscriptionBySubCode(req.getSubscriptionId());
//
//        if(sub == null || !"ACTIVE".equals(sub.getSubStatus())) {
//            return ResponseEntity.badRequest().body("취소할 구독이 없습니다.");
//        }
//
//        subscriptionService.cancelSubscription(sub.getSubCode());
//
//        // Toss / PG사 환불 처리: billing_key 사용
//        paymentService.refundByBillingKey(req.getBillingKey(), req.getAmount());
//
//        return ResponseEntity.ok().body("환불 처리 완료");
//    }
    /**
     * [정책 2] '생성' 버튼을 처리합니다.
     * 성한 새로운 ID를 로그인 ID로 사용하여 계정을 활성화합니다.
     */
    @PostMapping("/activate-custom")
    public ResponseEntity<String> activateCustomLogins(@RequestBody List<String> empIds) {
        // 컨트롤러는 "새로운 ID로 활성화 해줘" 라는 요청만 전달합니다.
        empIds.forEach(empLoginService::activateCustomLogin);
        return ResponseEntity.ok("선택된 사원들의 계정을 [신규 ID]로 생성하고, 초기 비밀번호를 발송했습니다.");
    }
    
    //아이디 중복체크
    @GetMapping("/checkId")
    public Map<String, Boolean> checkUsername(@RequestParam String empId) {
        boolean exists = empLoginService.idChecks(empId);
        return Map.of("exists", exists);
    }
    //사원권한 직접변경
    @PostMapping("/admin/update-role") 
    public ResponseEntity<?> updateUserRole(@RequestBody Map<String, String> payload) {
        try {
            String empId = payload.get("empId");
            String newRole = payload.get("newRole");

            if (empId == null || empId.trim().isEmpty() ||
                newRole == null || newRole.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "empId와 newRole은 필수입니다."));
            }

            // 2-2에서 만든 서비스 메소드 호출
            empLoginService.updateEmployeeRole(empId, newRole);

            String successMsg = String.format("사원(%s)의 권한이 [%s](으)로 변경되었습니다.", empId, newRole);
            return ResponseEntity.ok(Map.of("message", successMsg));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("message", "서버 오류: " + e.getMessage()));
        }
    }
    @PostMapping("/activate-custom-with-role")
    public ResponseEntity<?> activateCustomLoginsWithRole(@RequestBody Map<String, Object> payload) {
        try {
            // 1. empIds 안전하게 변환
            Object empIdsObj = payload.get("empIds");
            List<String> empIds = new ArrayList<>();
            if (empIdsObj instanceof List<?>) {
                for (Object o : (List<?>) empIdsObj) {
                    empIds.add(o.toString());
                }
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "empIds 형식이 잘못되었습니다."));
            }

            // 2. role 추출
            String role = (String) payload.get("role");
            if (role == null || role.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "role은 필수입니다."));
            }

            // 3. role 유효성 체크
            Set<String> validRoles = Set.of("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER", "ROLE_GUEST");
            if (!validRoles.contains(role.toUpperCase())) {
                return ResponseEntity.badRequest().body(Map.of("message", "유효하지 않은 권한: " + role));
            }

            // 4. 서비스 호출
            empLoginService.activateCustomLoginWithRole(empIds, role.toUpperCase());

            String msg = String.format("선택된 %d명의 계정을 [신규 ID]로 생성하고 [%s] 권한을 부여했습니다.", empIds.size(), role.toUpperCase());
            return ResponseEntity.ok(Map.of("message", msg));

        } catch (Exception e) {
            e.printStackTrace(); // 로그 찍기
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("message", "서버 오류: " + e.getMessage()));
        }
    }
}
