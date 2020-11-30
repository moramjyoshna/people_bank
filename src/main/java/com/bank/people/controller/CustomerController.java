package com.bank.people.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bank.people.util.BankConstants;

@RestController
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

	@Autowired
	CustomerService customerService;

	/**
	 * This endpoint is used for customer login functionality
	 * 
	 * @param userLoginRequestDto
	 * @return CustomerResponseDto with status code and message
	 * @throws CustomerNotFoundException
	 * @throws InvalidCustomerIdException
	 */
	@PostMapping("/login")
	public ResponseEntity<CustomerResponseDto> customerLogin(@RequestBody CustomerRequestDto customerRequestDto)
			throws CustomerNotFoundException {
		
		logger.info(BankConstants.CUSTOMER_LOGIN_CONTROLLER);
		CustomerResponseDto responseDto = customerService.customerLogin(customerRequestDto);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

}
