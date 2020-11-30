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

import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import com.bank.people.entity.Account;
import com.bank.people.entity.Beneficiary;
import com.bank.people.util.BankConstants;

import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.IbanNumberNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;
import com.bank.people.repository.AccountRepository;
import com.bank.people.repository.BeneficiaryRepository;
import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.entity.Bank;
import com.bank.people.entity.Customer;
import com.bank.people.exception.BeneficiariesNotFound;
import com.bank.people.exception.CustomerNotFoundException;
import com.bank.people.repository.BankRepository;
import com.bank.people.repository.CustomerRepository;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
	
	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);

	@Autowired
	BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	AccountRepository accountRepository;

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

	@Override
	public List<BeneficiaryResponseDto> getBeneficiaryList(Integer customerId, Integer pageNumber)
			throws BeneficiariesNotFound, CustomerNotFoundException {
		logger.info(BankConstants.LOG_GET_BENEFICIARY_LIST);
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
					.setBankName(bankMap.get(Integer.parseInt(beneficiary.getBeneficiaryIbanNumber().substring(4, 8))));
			beneficiaryResponseDtos.add(beneficiaryResponseDto);
		});
		logger.info(BankConstants.LOG_EXIST_BENEFICIARY_LIST);
		return beneficiaryResponseDtos;
	}
}
