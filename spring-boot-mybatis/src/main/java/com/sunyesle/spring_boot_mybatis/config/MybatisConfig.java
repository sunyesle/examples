package com.sunyesle.spring_boot_mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.sunyesle.spring_boot_mybatis")
public class MybatisConfig {
}
