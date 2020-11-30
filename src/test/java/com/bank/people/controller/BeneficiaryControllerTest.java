package com.bank.people.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;
import com.bank.people.service.BeneficiaryService;
import com.bank.people.util.BankConstants;

@RunWith(MockitoJUnitRunner.class)
public class BeneficiaryControllerTest {

	@Mock
	BeneficiaryService beneficiaryService;

	@InjectMocks
	BeneficiaryController beneficiaryController;

	Integer beneficiaryId = 1;

	@Test
	public void removeBeneficiaryOK() throws RemoveBeneficaryException, BeneficaryNotFoundException {
		RemoveBeneficiaryResponseDto responseDto = new RemoveBeneficiaryResponseDto();
		responseDto.setMessage(BankConstants.BENEFICIARY_REMOVED_SUCCESSFULLY);
		responseDto.setStatusCode(HttpStatus.OK.value());
		Mockito.when(beneficiaryService.deleteBeneficiary(Mockito.any())).thenReturn(responseDto);

		ResponseEntity<RemoveBeneficiaryResponseDto> response = beneficiaryController.deleteBeneficiary(beneficiaryId);
		// expected actual
		Assert.assertEquals(200, response.getStatusCodeValue());
		Assert.assertEquals(BankConstants.BENEFICIARY_REMOVED_SUCCESSFULLY, response.getBody().getMessage());
	}

}
