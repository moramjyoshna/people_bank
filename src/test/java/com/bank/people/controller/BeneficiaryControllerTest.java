package com.bank.people.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
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

import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.IbanNumberNotFoundException;
import com.bank.people.service.BeneficiaryService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class BeneficiaryControllerTest {

	@Mock
	BeneficiaryService beneficiaryService;

	@InjectMocks
	BeneficiaryController beneficiaryController;

	UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto = new UpdateBeneficiaryRequestDto();
	UpdateBeneficiaryResponseDto updateBeneficiaryResponseDto = new UpdateBeneficiaryResponseDto();

	@BeforeAll
	public void setup() {
		updateBeneficiaryRequestDto.setBeneficiaryIbanNumber("SA12 1000 099 12345");
		updateBeneficiaryRequestDto.setBeneficiaryName("Manisha");;
		
		updateBeneficiaryResponseDto.setMessage("Success");
		updateBeneficiaryResponseDto.setStatusCode("200");
	}
	
	@Test
	void testUpdateBeneficiary() throws BeneficaryNotFoundException, IbanNumberNotFoundException {
		Mockito.when(beneficiaryService.updateBeneficiary(Mockito.any(),Mockito.anyInt())).thenReturn(updateBeneficiaryResponseDto);
		ResponseEntity<UpdateBeneficiaryResponseDto> actual = beneficiaryController.updateBeneficiary(updateBeneficiaryRequestDto, 1);
		Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

}
