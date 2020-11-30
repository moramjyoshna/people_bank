package com.bank.people.service;

import com.bank.people.dto.BeneficiaryRequestDto;
import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.exception.BeneficiaryException;

public interface BeneficiaryService {

	BeneficiaryResponseDto addBeneficiary(BeneficiaryRequestDto beneficiaryRequestDto) throws BeneficiaryException;
}
