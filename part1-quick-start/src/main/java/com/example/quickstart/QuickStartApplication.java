package com.example.quickstart;

import com.example.quickstart.repository.UserAnnotationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@SpringBootApplication
public class QuickStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuickStartApplication.class, args);
    }

    @Resource
    private UserAnnotationRepository repository;

    @RestController
    @RequestMapping("/user")
    public class UserController {
        @Transactional
        @RequestMapping("/update/age")
        public Boolean updateAgeByUsername(){
            repository.updateAgeByName("test2", 26);
            return true;
        }
    }
}
