package com.example.customerapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customerapp.exception.ResourceNotFoundException;
import com.example.customerapp.model.Customer;
import com.example.customerapp.repository.CustomerRepository;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepo;

	@GetMapping("/getAllCustomers")
	public List<Customer> getAllCustomer() {
		return customerRepo.findAll();
	}

	@PostMapping("/addCustomers")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws Exception {
		Customer custObject = null;
		try {
			custObject = customerRepo.save(customer);
		} catch (Exception e) {
			throw new Exception("Customer Create Failed" + e);
		}
		return ResponseEntity.ok().body(custObject);
	}

	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long id,
			@RequestBody Customer customerDetails) throws Exception {

		Customer findCustomer = customerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));

		findCustomer.setEmailId(customerDetails.getEmailId());
		findCustomer.setLastName(customerDetails.getLastName());
		findCustomer.setFirstName(customerDetails.getFirstName());
		
		 Customer updatedCustomer = null;
		try {
			updatedCustomer = customerRepo.save(findCustomer);
		} catch (Exception e) {
			throw new Exception("Customer Update Failed" + e);
		}
		return ResponseEntity.ok(updatedCustomer);
	}

}
