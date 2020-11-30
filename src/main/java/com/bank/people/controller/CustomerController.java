package com.bank.people.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.people.dto.CustomerRequestDto;
import com.bank.people.dto.CustomerResponseDto;
import com.bank.people.exception.CustomerNotFoundException;
import com.bank.people.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/login")
	public ResponseEntity<CustomerResponseDto> customerLogin(@RequestBody CustomerRequestDto customerRequestDto)
	throws CustomerNotFoundException{
		CustomerResponseDto responseDto = customerService.customerLogin(customerRequestDto);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}


}
