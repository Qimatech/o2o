package com.qima.o2o;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class O2oApplication {

	public static void main(String[] args) {
		System.out.println("o2o2---->");
		System.out.println("----o2o1---->");
		SpringApplication.run(O2oApplication.class, args);
		System.out.println("o2o2---->");
		System.out.println("----o2o1---->");
	}
}
