package com.springboot.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplicatopn {

    private static final Logger log = LoggerFactory.getLogger(ServerApplicatopn.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerApplicatopn.class, args);
    }

}
