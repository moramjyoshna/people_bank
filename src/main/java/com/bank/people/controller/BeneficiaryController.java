package com.bank.people.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.IbanNumberNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;
import com.bank.people.service.BeneficiaryService;
import com.bank.people.util.BankConstants;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/beneficiaries")
public class BeneficiaryController {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

	@Autowired
	BeneficiaryService beneficiaryService;
	
	/**
	 * This endpoint is used to remove a beneficiary
	 * 
	 * @param beneficiaryId
	 * @return RemoveBeneficiaryResponseDto with status code and message
	 * @throws RemoveBeneficaryException
	 * @throws BeneficaryNotFoundException
	 */
	@DeleteMapping("/{beneficiaryId}")
	public ResponseEntity<RemoveBeneficiaryResponseDto> deleteBeneficiary(@PathVariable Integer beneficiaryId)
			throws RemoveBeneficaryException, BeneficaryNotFoundException {

		logger.info(BankConstants.REMOVE_BENEFICIARY);
		RemoveBeneficiaryResponseDto response = beneficiaryService.deleteBeneficiary(beneficiaryId);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/**
	 * This method updates the training if existing user wants to change the existing trainingId
	 * 
	 * @author Manisha
	 * @param updateEnrollmentRequestDto
	 * 
	 * @throws InvalidEnrollmentIdException when EnrollmentID not found in database & DuplicateEnrollmentException when user is updating with same existing session
	 * @return UpdateEnrollmentResponseDto will return the response of successfully updated the Training 
	 * @throws BeneficaryNotFoundException 
	 * @throws IbanNumberNotFoundException 
	 */
	@PutMapping("/beneficiaries/{beneficiaryId}")
	public ResponseEntity<UpdateBeneficiaryResponseDto> updateBeneficiary(@RequestBody UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto, @RequestParam("beneficiaryId") Integer beneficiaryId) throws BeneficaryNotFoundException, IbanNumberNotFoundException
	{
		logger.info(BankConstants.BENEFICIARY_CONTROLLER);
		return new ResponseEntity<>(beneficiaryService.updateBeneficiary(updateBeneficiaryRequestDto, beneficiaryId), HttpStatus.OK);
	}

}
