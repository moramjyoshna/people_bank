package com.bank.people.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

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

import com.bank.people.controller.BeneficiaryController;
import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import com.bank.people.entity.Account;
import com.bank.people.entity.Beneficiary;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.IbanNumberNotFoundException;
import com.bank.people.repository.AccountRepository;
import com.bank.people.repository.BeneficiaryRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BeneficiaryServiceImplTest {

	@InjectMocks
	BeneficiaryServiceImpl beneficiaryServiceImpl;
	
	@Mock
	BeneficiaryRepository beneficiaryRepository;
	
	@Mock
	AccountRepository accountRepository;
	

	Integer beneficiaryId;
	UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto = new UpdateBeneficiaryRequestDto();
	UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto1 = new UpdateBeneficiaryRequestDto();

	UpdateBeneficiaryResponseDto updateBeneficiaryResponseDto = new UpdateBeneficiaryResponseDto();
	Beneficiary beneficiary = new Beneficiary();
	Account account = new Account();

	@BeforeAll
	public void setup() {
		updateBeneficiaryRequestDto.setBeneficiaryIbanNumber("SA12 1000 099 12345");
		updateBeneficiaryRequestDto.setBeneficiaryName("Manisha");
		updateBeneficiaryRequestDto1.setBeneficiaryIbanNumber("SA12 1000 099 12346");
		updateBeneficiaryRequestDto1.setBeneficiaryName("Manisha");
		beneficiaryId = 1;
		beneficiary.setAccountId(1);
		beneficiary.setBeneficiaryIbanNumber("SA12 1000 099 12345");
		beneficiary.setBeneficiaryName("Manisha");
		beneficiary.setBeneficiaryId(2);
		account.setAccountId(1);
		account.setIbanNumber("SA12 1000 099 12345");
		updateBeneficiaryResponseDto.setMessage("Success");
		updateBeneficiaryResponseDto.setStatusCode("200");
	}

	@Test
	void testUpdateBeneficiary() throws BeneficaryNotFoundException, IbanNumberNotFoundException {
		Mockito.when(beneficiaryRepository.findByBeneficiaryId(Mockito.anyInt())).thenReturn(Optional.of(beneficiary));
		Mockito.when(accountRepository.findByAccountId(Mockito.anyInt())).thenReturn(Optional.of(account));
		UpdateBeneficiaryResponseDto actual = beneficiaryServiceImpl.updateBeneficiary(updateBeneficiaryRequestDto, beneficiaryId);
	    assertThat(Optional.of(beneficiary)).isNotEmpty();
		assertThat(Optional.of(account)).isNotEmpty();
		Assert.assertEquals("200", actual.getStatusCode());
	}

	@Test
	  void testUpdateBeneficiaryFail() {
		  Mockito.when(beneficiaryRepository.findByBeneficiaryId(Mockito.any())).thenReturn(Optional.of(beneficiary));
		  Mockito.when(accountRepository.findByAccountId(Mockito.anyInt())).thenReturn(Optional.of(account));
		  Exception exception = assertThrows(IbanNumberNotFoundException.class, () ->
		  beneficiaryServiceImpl.updateBeneficiary(updateBeneficiaryRequestDto1,beneficiaryId));
		  assertThat(Optional.of(beneficiary)).isNotEmpty();
		  assertThat(Optional.of(account)).isNotEmpty();
	      assertEquals("IBAN Number does not exist!", exception.getMessage());
	  }
}
