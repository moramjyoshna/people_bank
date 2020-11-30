package com.bank.people.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bank.people.controller.BeneficiaryController;
import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.entity.Bank;
import com.bank.people.entity.Beneficiary;
import com.bank.people.entity.Customer;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.BeneficiariesNotFound;
import com.bank.people.exception.CustomerNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;
import com.bank.people.repository.BankRepository;
import com.bank.people.repository.BeneficiaryRepository;
import com.bank.people.repository.CustomerRepository;
import com.bank.people.util.BankConstants;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

	@Autowired
	BeneficiaryRepository beneficiaryRepository;

	@Autowired
	BankRepository bankRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public RemoveBeneficiaryResponseDto deleteBeneficiary(Integer beneficiaryId)
			throws RemoveBeneficaryException, BeneficaryNotFoundException {

		Optional<Beneficiary> beneficiary = beneficiaryRepository.findById(beneficiaryId);
		if (!beneficiary.isPresent()) {
			throw new BeneficaryNotFoundException(BankConstants.BENEFICIARY_DOES_NOT_EXISTS);
		}

		try {
			beneficiaryRepository.deleteById(beneficiaryId);
			logger.info(BankConstants.BENEFICIARY_REMOVED_SUCCESSFULLY);
			RemoveBeneficiaryResponseDto responseDto = new RemoveBeneficiaryResponseDto();
			responseDto.setMessage(BankConstants.BENEFICIARY_REMOVED_SUCCESSFULLY);
			responseDto.setStatusCode(HttpStatus.OK.value());
			return responseDto;
		} catch (Exception e) {
			logger.info(BankConstants.FAILED_TO_REMOVE_BENEFICIARY);
			throw new RemoveBeneficaryException(BankConstants.FAILED_TO_REMOVE_BENEFICIARY);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<BeneficiaryResponseDto> getBeneficiaryList(Integer customerId, Integer pageNumber)
			throws BeneficiariesNotFound, CustomerNotFoundException {
		Pageable paging = PageRequest.of(pageNumber, BankConstants.SIZE, Sort.by("beneficiaryName"));
		Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
		if (!customer.isPresent()) {
			throw new CustomerNotFoundException(BankConstants.CUSTOMER_NOT_FOUND);
		}
		Optional<List<Beneficiary>> beneficiaryList = beneficiaryRepository.findByCustomerId(customerId,paging);
		if (!beneficiaryList.isPresent()) {
			throw new BeneficiariesNotFound(BankConstants.NO_BENEFICIARY_FOUND);
		}
		HashMap<Integer, String> bankMap = new HashMap<>();
		List<Bank> bankList = bankRepository.findAll();
		bankList.forEach(bank -> bankMap.put(bank.getBankCode(), bank.getBankName()));
		List<BeneficiaryResponseDto> beneficiaryResponseDtos = new ArrayList<>();
		beneficiaryList.get().forEach(beneficiary -> {
			BeneficiaryResponseDto beneficiaryResponseDto = new BeneficiaryResponseDto();
			beneficiaryResponseDto.setBeneficiaryName(beneficiary.getBeneficiaryName());
			beneficiaryResponseDto.setIbanNumber(beneficiary.getBeneficiaryIbanNumber());
			beneficiaryResponseDto
					.setBankName(bankMap.get(Integer.parseInt(beneficiaryResponseDto.getIbanNumber().substring(5, 9))));
			beneficiaryResponseDtos.add(beneficiaryResponseDto);
		});

		return beneficiaryResponseDtos;

	}
}
