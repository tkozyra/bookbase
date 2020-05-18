package com.bookbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class BookbaseApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BookbaseApplication.class, args);
    }

}
