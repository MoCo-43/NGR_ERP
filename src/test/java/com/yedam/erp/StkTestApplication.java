package com.yedam.erp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class StkTestApplication {


 @Test
  void test1(){
    System.out.println("Test");
    log.info("Test Test info");
  }


}
