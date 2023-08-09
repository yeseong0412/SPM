package com.example.spm.test.annotation;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


// 테스트시 반복되는 어노테이션 사용을 하지 않기 위해 만든 어노테이션
// TestInit
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public @interface TestInit {
}
