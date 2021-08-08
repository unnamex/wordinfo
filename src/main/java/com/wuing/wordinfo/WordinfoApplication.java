package com.wuing.wordinfo;

import com.wuing.wordinfo.utils.runAll;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@MapperScan("com.wuing.wordinfo.dao")
@SpringBootApplication
@EnableTransactionManagement
public class WordinfoApplication implements ApplicationRunner {
    @Autowired
    runAll runAll;

    public static void main(String[] args) {
        SpringApplication.run(WordinfoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
         runAll.runableWord();
    }
}
