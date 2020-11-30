package com.bank.people.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.people.dto.BeneficiaryRequestDto;
import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.exception.BeneficiaryException;
import com.bank.people.service.BeneficiaryService;
import com.bank.people.util.BankConstants;

@RestController
public class BeneficiaryController {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

	@Autowired
	BeneficiaryService beneficiaryService;

	@PostMapping("/beneficiary")
	public ResponseEntity<BeneficiaryResponseDto> addBeneficiary(
			@RequestBody BeneficiaryRequestDto beneficiaryRequestDto) throws BeneficiaryException {
		logger.info(BankConstants.BENEFICIARY_CONTROLLER);
		BeneficiaryResponseDto responseBeneficiaryDto = beneficiaryService.addBeneficiary(beneficiaryRequestDto);
		return new ResponseEntity<>(responseBeneficiaryDto, HttpStatus.CREATED);
	}

}
