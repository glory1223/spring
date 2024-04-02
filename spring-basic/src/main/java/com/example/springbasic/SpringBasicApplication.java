package com.example.springbasic;

import com.example.springbasic.ch01.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLOutput;

@SpringBootApplication
public class SpringBasicApplication {

	public static void main(String[] args) {
		//ApplicationContext: Spring Container에 저장된 bean객체 확인 가능.
		//ApplicationContext context = SpringApplication.run(SpringBasicApplication.class, args);
		SpringApplication.run(SpringBasicApplication.class, args);
		//ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		/*
		String[] beanDefinitionNames = context.getBeanDefinitionNames(); //bean이름을 가져온다.

		for (String beanDefinitionName : beanDefinitionNames) {
			Object bean = context.getBean(beanDefinitionName); //bean객체를 가져온다. 저장된 object 리턴
			System.out.println("name: " + beanDefinitionName + ", object: " + bean);
		}

		 */
	}

}
