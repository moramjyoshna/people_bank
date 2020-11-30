package com.bank.people.service;

import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.IbanNumberNotFoundException;
import java.util.List;

import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.exception.BeneficiariesNotFound;
import com.bank.people.exception.CustomerNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;

public interface BeneficiaryService {

	public RemoveBeneficiaryResponseDto deleteBeneficiary(Integer beneficiaryId)
			throws RemoveBeneficaryException, BeneficaryNotFoundException;

	public List<BeneficiaryResponseDto> getBeneficiaryList(Integer customerId, Integer pageNumber)
			throws BeneficiariesNotFound, CustomerNotFoundException;

	public UpdateBeneficiaryResponseDto updateBeneficiary(UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto, Integer beneficiaryId) throws BeneficaryNotFoundException, IbanNumberNotFoundException;

}
