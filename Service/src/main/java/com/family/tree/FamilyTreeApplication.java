package com.family.tree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// this is to disable default login page from spring security
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FamilyTreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyTreeApplication.class, args);
    }
}
