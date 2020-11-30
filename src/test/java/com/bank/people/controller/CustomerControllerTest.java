package com.bank.people.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bank.people.dto.CustomerRequestDto;
import com.bank.people.dto.CustomerResponseDto;
import com.bank.people.exception.CustomerNotFoundException;
import com.bank.people.service.CustomerService;
import com.bank.people.util.BankConstants;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerControllerTest {

	@InjectMocks
	CustomerController customerController;

	@Mock
	CustomerService customerService;

	@Test
	public void testCustomerLogin() throws CustomerNotFoundException {

		CustomerResponseDto customerResponseDTO = new CustomerResponseDto();
		customerResponseDTO.setMessage(BankConstants.LOGIN_SUCCESS);
		customerResponseDTO.setStatusCode(200);

		CustomerRequestDto customerRequestDto = new CustomerRequestDto();

		Mockito.when(customerService.customerLogin(customerRequestDto)).thenReturn(customerResponseDTO);

		ResponseEntity<CustomerResponseDto> login = customerController.customerLogin(customerRequestDto);

		assertEquals(HttpStatus.OK, login.getStatusCode());
		assertEquals(BankConstants.LOGIN_SUCCESS, login.getBody().getMessage());
		assertEquals(HttpStatus.OK.value(), login.getBody().getStatusCode());

	}

}
