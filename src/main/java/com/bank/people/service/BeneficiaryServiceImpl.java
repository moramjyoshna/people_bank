package com.bank.people.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import com.bank.people.entity.Account;
import com.bank.people.entity.Beneficiary;
import com.bank.people.util.BankConstants;


import org.springframework.http.HttpStatus;

import com.bank.people.controller.BeneficiaryController;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.IbanNumberNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;
import com.bank.people.repository.AccountRepository;
import com.bank.people.repository.BeneficiaryRepository;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
	
	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);

	@Autowired
	BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public RemoveBeneficiaryResponseDto deleteBeneficiary(Integer beneficiaryId) throws RemoveBeneficaryException, BeneficaryNotFoundException {
		
		Optional<Beneficiary> beneficiary = beneficiaryRepository.findById(beneficiaryId);
		if(!beneficiary.isPresent()) {
		 throw new BeneficaryNotFoundException(BankConstants.BENEFICIARY_ID_DOES_NOT_EXISTS);
		}

		try {
			beneficiaryRepository.deleteById(beneficiaryId);
			logger.info(BankConstants.BENEFICIARY_REMOVED_SUCCESSFULLY);
			RemoveBeneficiaryResponseDto response = new RemoveBeneficiaryResponseDto();
			response.setMessage(BankConstants.BENEFICIARY_REMOVED_SUCCESSFULLY);
			response.setStatusCode(HttpStatus.OK.value());
			return response;
		} catch (Exception e) {
			logger.info(BankConstants.FAILED_TO_REMOVE_BENEFICIARY);
			throw new RemoveBeneficaryException(BankConstants.FAILED_TO_REMOVE_BENEFICIARY);
		}
	};
	
	@Override
	public UpdateBeneficiaryResponseDto updateBeneficiary(UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto,
			Integer beneficiaryId) throws BeneficaryNotFoundException, IbanNumberNotFoundException {
		logger.info(BankConstants.BENEFICIARY_UPDATE_SERVICE);
		UpdateBeneficiaryResponseDto updateBeneficiaryResponseDto = new UpdateBeneficiaryResponseDto();
		Optional<Beneficiary> beneficiary = beneficiaryRepository.findByBeneficiaryId(beneficiaryId);
		if(beneficiary.isPresent()){
			Optional<Account> account = accountRepository.findByAccountId(beneficiary.get().getAccountId());
			if(account.get().getIbanNumber().equals(updateBeneficiaryRequestDto.getBeneficiaryIbanNumber())) {
			beneficiary.get().setBeneficiaryId(beneficiaryId);
			beneficiary.get().setBeneficiaryName(updateBeneficiaryRequestDto.getBeneficiaryName());
			beneficiary.get().setBeneficiaryIbanNumber(updateBeneficiaryRequestDto.getBeneficiaryIbanNumber());
			beneficiaryRepository.save(beneficiary.get());
			}else {
				logger.info(BankConstants.IBAN_NUMBER_NOT_FOUND);
				throw new IbanNumberNotFoundException(BankConstants.IBAN_NUMBER_DOES_NOT_EXISTS);
			}
		}else {
			logger.info(BankConstants.BENEFICIARYID_NOT_FOUND);
			throw new BeneficaryNotFoundException(BankConstants.BENEFICIARY_ID_DOES_NOT_EXISTS);
		}
		updateBeneficiaryResponseDto.setMessage("Successfully added Beneficiary");
		updateBeneficiaryResponseDto.setStatusCode("200");
		return updateBeneficiaryResponseDto;
	}
}
