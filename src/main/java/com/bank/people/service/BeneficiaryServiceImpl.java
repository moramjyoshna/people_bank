package com.bank.people.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.people.controller.BeneficiaryController;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.entity.Beneficiary;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;
import com.bank.people.repository.BeneficiaryRepository;
import com.bank.people.util.BankConstants;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
	
	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

	@Autowired
	BeneficiaryRepository beneficiaryRepository;

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
}
