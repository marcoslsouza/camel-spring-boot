package com.github.marcoslsouza.controller;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.marcoslsouza.model.Employee;

@RestController
public class SpringRabbitMQController {

	@Produce(uri = "direct:startQueuePoint")
	private ProducerTemplate template;
	
	// Receives the employee data and uses the Camel ProducerTemplate to send the employee object to the queue
	@GetMapping("/employee")
	public String createEmployee(@RequestParam int id, @RequestParam String name, @RequestParam String designation) {
		
		Employee emp = new Employee();
		emp.setName(name);
		emp.setDesignation(designation);
		emp.setEmpId(id);
		
		this.template.asyncSendBody(this.template.getDefaultEndpoint(), emp);
		
		return "Employee data sent to queue!";
	}
}
