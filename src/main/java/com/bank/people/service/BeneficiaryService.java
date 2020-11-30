package com.bank.people.service;

import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;

public interface BeneficiaryService {
	
	public RemoveBeneficiaryResponseDto deleteBeneficiary(Integer beneficiaryId) throws RemoveBeneficaryException, BeneficaryNotFoundException;

}
