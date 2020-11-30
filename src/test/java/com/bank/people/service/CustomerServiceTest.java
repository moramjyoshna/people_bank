package com.bank.people.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bank.people.dto.CustomerRequestDto;
import com.bank.people.dto.CustomerResponseDto;
import com.bank.people.entity.Customer;
import com.bank.people.exception.CustomerNotFoundException;
import com.bank.people.repository.CustomerRepository;
import com.bank.people.util.BankConstants;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerServiceTest {

	@InjectMocks
	CustomerServiceImpl customerServiceImpl;

	@Mock
	CustomerRepository customerRepository;

	@Test
	public void testLoginSuccess() throws CustomerNotFoundException {

		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCustomerId(892786);
		customerRequestDto.setPassword("swathi");

		Customer customer = new Customer();

		Mockito.when(customerRepository.findByCustomerIdAndPassword(customerRequestDto.getCustomerId(),
				customerRequestDto.getPassword())).thenReturn(Optional.of(customer));

		CustomerResponseDto responseDTO = customerServiceImpl.customerLogin(customerRequestDto);
		assertEquals(BankConstants.LOGIN_SUCCESS, responseDTO.getMessage());
		assertEquals(HttpStatus.OK.value(), responseDTO.getStatusCode());
	}

	@Test
	public void testInvalidCustomerId() throws CustomerNotFoundException {
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCustomerId(89286);

		Assertions.assertThrows(CustomerNotFoundException.class, () -> {
			customerServiceImpl.customerLogin(customerRequestDto);
		});

	}

	@Test
	public void testInvalidCustomerCredentials() throws CustomerNotFoundException {
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCustomerId(89286);
		customerRequestDto.setPassword("swathi");

		Assertions.assertThrows(CustomerNotFoundException.class, () -> {
			customerServiceImpl.customerLogin(customerRequestDto);
		});
	}
}
