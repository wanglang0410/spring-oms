package com.oms.api;

import com.oms.api.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
class ApiApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Test
    void contextLoads() {
        log.info(passwordEncoder.encode("123456"));
        log.info(MD5Utils.encode("123456"));
    }

}
