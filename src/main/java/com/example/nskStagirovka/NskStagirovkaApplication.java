package com.example.nskStagirovka;

import com.example.nskStagirovka.service.ServiceEmployeeImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NskStagirovkaApplication {
    ServiceEmployeeImpl serviceEmployee;
	public static void main(String[] args) {
		SpringApplication.run(NskStagirovkaApplication.class, args);
	}

}
