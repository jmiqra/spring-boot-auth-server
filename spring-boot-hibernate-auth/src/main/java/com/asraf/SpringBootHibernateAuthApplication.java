package com.asraf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.asraf.configs.SpringConfig;

@SpringBootApplication
@ComponentScan(basePackageClasses = SpringConfig.class)
public class SpringBootHibernateAuthApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHibernateAuthApplication.class, args);
	}
}
