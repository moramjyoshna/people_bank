package com.bank.people.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.BeneficiariesNotFound;
import com.bank.people.exception.CustomerNotFoundException;
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
	 * This method is used to fetch the approvals list for the loggedIn approver
	 * 
	 * @param customerId
	 * @param pageNumber
	 * @throws BeneficiariesNotFound 
	 *
	 */
	@GetMapping(value = "/customers/{customerId}/beneficiaries")
	public ResponseEntity<List<BeneficiaryResponseDto>> getBeneficiaryList(@PathVariable Integer customerId,
			@RequestParam Integer pageNumber) throws BeneficiariesNotFound, CustomerNotFoundException {
		// log.info("Entered into Benifitiary controller getBeneficiaryList method ");
		List<BeneficiaryResponseDto> beneficiaryResponseDto = beneficiaryService.getBeneficiaryList(customerId,
				pageNumber);
		return new ResponseEntity<>(beneficiaryResponseDto, HttpStatus.OK);

	}

}
