package com.bank.people.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.BeneficiariesNotFound;
import com.bank.people.exception.CustomerNotFoundException;
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
	
	Integer pageNumber = 1;

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
	
	@Test
	public void testGetBeneficiaryList() throws BeneficiariesNotFound, CustomerNotFoundException {
		List<BeneficiaryResponseDto> dtos = new ArrayList<>();
		BeneficiaryResponseDto beneficiaryResponseDto = new BeneficiaryResponseDto();
		beneficiaryResponseDto.setBeneficiaryName("Sravani");
		beneficiaryResponseDto.setIbanNumber("ESOP 1234");
		beneficiaryResponseDto.setBankName("People Bank");
		Mockito.when(beneficiaryService.getBeneficiaryList(1, 1)).thenReturn(dtos);
		ResponseEntity<List<BeneficiaryResponseDto>> beneficiaries = beneficiaryController.getBeneficiaryList(beneficiaryId,pageNumber);
		Assert.assertEquals(HttpStatus.OK, beneficiaries.getStatusCode());

	}

}
