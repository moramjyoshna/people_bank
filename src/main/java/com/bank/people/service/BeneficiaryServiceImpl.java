package com.bank.people.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.people.dto.BeneficiaryRequestDto;
import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.dto.RemoveBeneficiaryResponseDto;
import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import com.bank.people.entity.Account;
import com.bank.people.entity.Bank;
import com.bank.people.entity.Beneficiary;
import com.bank.people.entity.Customer;
import com.bank.people.exception.BeneficaryNotFoundException;
import com.bank.people.exception.BeneficiariesNotFound;
import com.bank.people.exception.BeneficiaryException;
import com.bank.people.exception.CustomerNotFoundException;
import com.bank.people.exception.IbanNumberNotFoundException;
import com.bank.people.exception.RemoveBeneficaryException;
import com.bank.people.repository.AccountRepository;
import com.bank.people.repository.BankRepository;
import com.bank.people.repository.BeneficiaryRepository;
import com.bank.people.repository.CustomerRepository;
import com.bank.people.util.BankConstants;

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
		Optional<List<Beneficiary>> beneficiaryList = beneficiaryRepository.findByCustomerId(customerId, paging);
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

	@Override
	public BeneficiaryResponseDto addBeneficiary(BeneficiaryRequestDto beneficiaryRequestDto)
			throws BeneficiaryException {
		logger.info(BankConstants.BENEFICIARY_SERVICE_IMPL);
		Optional<Account> customerAccount = accountRepository.findByCustomerId(beneficiaryRequestDto.getCustomerId());
		/*
		 * Optional<Beneficiary> beneficiaryAccount = beneficiaryRepository
		 * .findByBeneficiaryIbanNumber(beneficiaryRequestDto.getBeneficiaryIbanNumber()
		 * );
		 */

		if (!validateBeneficiaryName(beneficiaryRequestDto.getBeneficiaryName())) {
			throw new BeneficiaryException(BankConstants.INVALID_BENEFICIARY_NAME);
		}

		if (!validIbanNo(beneficiaryRequestDto.getBeneficiaryIbanNumber())) {
			throw new BeneficiaryException(BankConstants.INVALID_BENEFICIARY_NUMBER);
		}

		if (!customerAccount.isPresent()) {
			throw new BeneficiaryException(BankConstants.NO_CUSTOMER_FOUND);
		}

		String ibanNumber = beneficiaryRequestDto.getBeneficiaryIbanNumber().replaceAll("\\s", "");
		Integer code = Integer.parseInt(ibanNumber.substring(4, 8));
		Optional<Bank> bank = bankRepository.findByBankCode(code);
		logger.info("Bank code", code);

		if (!bank.isPresent()) {
			throw new BeneficiaryException(BankConstants.BANK_CODE_NOT_EXISTS);
		}

		if (customerAccount.get().getIbanNumber().equals(beneficiaryRequestDto.getBeneficiaryIbanNumber())) {
			throw new BeneficiaryException(BankConstants.CANNOT_ADD_OWN_ACCOUNT);
		}

		Optional<Beneficiary> existBeneficiary = beneficiaryRepository.findByCustomerIdAndBeneficiaryIbanNumber(
				beneficiaryRequestDto.getCustomerId(), beneficiaryRequestDto.getBeneficiaryIbanNumber());
		if (existBeneficiary.isPresent()) {
			throw new BeneficiaryException(BankConstants.DUPLICATE_BENEFICIARY);
		}

		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setAccountId(beneficiaryRequestDto.getCustomerId());
		beneficiary.setCustomerId(beneficiaryRequestDto.getCustomerId());
		beneficiary.setBeneficiaryIbanNumber(beneficiaryRequestDto.getBeneficiaryIbanNumber());
		beneficiary.setBeneficiaryName(beneficiaryRequestDto.getBeneficiaryName());
		beneficiary.setBankId(bank.get().getBankId());

		Beneficiary response = beneficiaryRepository.save(beneficiary);

		if (response.getBeneficiaryId() == null) {
			throw new BeneficiaryException(BankConstants.ADD_BENEFICIARY_FAILED);
		}
		BeneficiaryResponseDto beneficiaryResponseDto = new BeneficiaryResponseDto();
		beneficiaryResponseDto.setBeneficiaryId(response.getBeneficiaryId());
		beneficiaryResponseDto.setBeneficiaryName(response.getBeneficiaryName());
		beneficiaryResponseDto.setMessage("Beneficiary added successfully");
		beneficiaryResponseDto.setStatusCode(201);
		return beneficiaryResponseDto;
	}

	private boolean validateBeneficiaryName(String beneficiaryName) {
		String name = ("^[a-zA-Z1-9'-]*$");
		return beneficiaryName.matches(name);
	}

	private boolean validIbanNo(String ibanNo) {

		Pattern p = Pattern.compile("[A-Z]{4} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4}", Pattern.CASE_INSENSITIVE);
		java.util.regex.Matcher m = p.matcher(ibanNo);
		return (m.find() && m.group().equals(ibanNo));
	}
}
