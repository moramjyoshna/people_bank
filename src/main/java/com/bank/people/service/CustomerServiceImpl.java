package com.bank.people.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.people.controller.BeneficiaryController;
import com.bank.people.dto.CustomerRequestDto;
import com.bank.people.dto.CustomerResponseDto;
import com.bank.people.entity.Customer;
import com.bank.people.exception.CustomerNotFoundException;
import com.bank.people.repository.CustomerRepository;
import com.bank.people.util.BankConstants;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public CustomerResponseDto customerLogin(CustomerRequestDto requestDto) throws CustomerNotFoundException {

		CustomerResponseDto responseDto = new CustomerResponseDto();

		if (validCustomerId(requestDto.getCustomerId()) != BankConstants.CUSTOMER_ID_COUNT) {
			logger.info(BankConstants.INVALID_CUSTOMER_ID);
			throw new CustomerNotFoundException(BankConstants.INVALID_CUSTOMER_ID);
		} else {
			Optional<Customer> customer = customerRepository.findByCustomerIdAndPassword(requestDto.getCustomerId(),
					requestDto.getPassword());
			if (!customer.isPresent()) {
				logger.info(BankConstants.CUSTOMER_NOT_FOUND);
				throw new CustomerNotFoundException(BankConstants.CUSTOMER_NOT_FOUND);
			} else {
				logger.info(BankConstants.LOGIN_SUCCESS);
				responseDto.setMessage(BankConstants.LOGIN_SUCCESS);
				responseDto.setStatusCode(HttpStatus.OK.value());
				return responseDto;
			}
		}
	}

	private Integer validCustomerId(Integer customerId) {
		Integer count = BankConstants.EMPTY_VALUE;
		while (customerId != BankConstants.EMPTY_VALUE) {
			customerId = customerId / 10;
			count++;
		}
		return count;
	}
}
