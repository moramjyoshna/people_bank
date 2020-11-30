package com.bank.people.service;

import com.bank.people.dto.CustomerRequestDto;
import com.bank.people.dto.CustomerResponseDto;
import com.bank.people.exception.CustomerNotFoundException;

public interface CustomerService {
	public CustomerResponseDto customerLogin(CustomerRequestDto customerRequest) throws CustomerNotFoundException;
}
