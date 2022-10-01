package com.example.managespending;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.managespending.data.remotes.repositories")
@EntityScan(basePackages = "com.example.managespending.data.models.entities")
public class ManagespendingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagespendingApplication.class, args);

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
