package com.bank.people.service;

import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.IbanNumberNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;

public interface BeneficiaryService {
	
	public RemoveBeneficiaryResponseDto deleteBeneficiary(Integer beneficiaryId) throws RemoveBeneficaryException, BeneficaryNotFoundException;

	public UpdateBeneficiaryResponseDto updateBeneficiary(UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto, Integer beneficiaryId) throws BeneficaryNotFoundException, IbanNumberNotFoundException;

}
