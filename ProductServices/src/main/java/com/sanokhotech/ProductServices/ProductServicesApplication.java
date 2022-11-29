package com.sanokhotech.ProductServices;

import com.sanokhotech.ProductServices.command.api.exception.ProductServiceEventsErrorHandler;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServicesApplication {
     /* CQRS Design Pattern
     * Command and Query Responsibility Segregation
     *
     * An event-driven architecture uses events to trigger and
     * communicate between decoupled services and is common
     * in modern applications built with microservices.
     * An event is a change in state, or an update,
     * like an item being placed in a shopping cart
     * on an e-commerce website.
     * */
	public static void main(String[] args) {
		SpringApplication.run(ProductServicesApplication.class, args);
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer){
		configurer.registerListenerInvocationErrorHandler(
				"product",
				configuration -> new ProductServiceEventsErrorHandler()
		);
	}
}
