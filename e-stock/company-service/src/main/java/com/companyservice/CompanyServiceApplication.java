package com.companyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * main class
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2
@CrossOrigin(origins = "*")
public class CompanyServiceApplication {

	/**
	 * main method -> application start
	 * @param args -> args for command line runner
	 */
	public static void main(String[] args) {
		SpringApplication.run(CompanyServiceApplication.class, args);
	}

}
