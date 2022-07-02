package org.csu.laomall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.csu.laomall.persistence")
public class LaoMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaoMallApplication.class, args);
    }

}
