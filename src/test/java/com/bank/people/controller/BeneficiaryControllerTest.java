package com.bank.people.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.IbanNumberNotFoundException;
import com.bank.people.exception.BeneficiariesNotFound;
import com.bank.people.exception.CustomerNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;
import com.bank.people.service.BeneficiaryService;
import com.bank.people.util.BankConstants;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BeneficiaryControllerTest {

	@Mock
	BeneficiaryService beneficiaryService;

	@InjectMocks
	BeneficiaryController beneficiaryController;
	Integer beneficiaryId = 1;
	Integer pageNumber = 1;
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
