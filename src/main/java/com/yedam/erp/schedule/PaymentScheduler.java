package com.yedam.erp.schedule;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yedam.erp.service.main.SubscriptionService;
import com.yedam.erp.vo.main.SubscriptionVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentScheduler {
    
    private final SubscriptionService subscriptionService;

    @Scheduled(cron = "0 0 4 * * *") // 매일 새벽 4시 실행
    public void scheduleDailyPayments() {
        log.info("===========  자동 결제 스케줄러 시작 ===========");
        
        List<SubscriptionVO> dueSubscriptions = subscriptionService.findSubscriptionsDueForPaymentToday();
        
        if (dueSubscriptions.isEmpty()) {
            log.info("오늘 자동 결제 대상이 없습니다.");
        } else {
            log.info("총 {}건의 자동 결제를 시작합니다.", dueSubscriptions.size());
        }

        for (SubscriptionVO sub : dueSubscriptions) {
            try {
                // 실제 결제를 요청하는 서비스 로직 호출!
                subscriptionService.processAutomaticPayment(sub);
            } catch (Exception e) {
                log.error("자동 결제 처리 중 최종 오류 발생 - 구독 코드: {}", sub.getSubCode(), e.getMessage());
            }
        }
        log.info("=========== 자동 결제 스케줄러 종료 ===========");
    }
}