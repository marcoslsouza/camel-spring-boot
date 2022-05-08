package com.github.marcoslsouza.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import com.github.marcoslsouza.model.Employee;

@Component
public class RabbitMQRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		// We will need to marshal the employee object to JSON. For this we will make use of Apache Camel JacksonDataFormat.
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);
		
		// Send it to the rabbitmq queue named javainuse. Also a rabbitmq exchange named javainuse is created
		// Returns Exception in case of queue error "guaranteedDeliveries=true".
		// It guarantees mandatory sending of the message "mandatory=true".
		from("direct:startQueuePoint")
			.id("idOfQueueHere")
			.marshal(jsonDataFormat)
			.to("rabbitmq://localhost:5672/ex-javainuse?queue=queue-javainuse&username=admin&password=admin&autoDelete=false&"
					+ "guaranteedDeliveries=true&mandatory=true").end();
	}
}
