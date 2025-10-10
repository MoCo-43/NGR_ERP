package com.yedam.erp.web.ApiController.main;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.hr.EmpService;
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
    @GetMapping
    public List<EmpVO> getEmployees(@RequestParam(required = false) String deptCode) {
        // 사원 정보 조회는 EmpService의 책임입니다.
        return empLoginService.findEmployeesByDept(deptCode);
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
	
}
