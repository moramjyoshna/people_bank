package com.bank.people.service;

import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.people.dto.BeneficiaryRequestDto;
import com.bank.people.dto.BeneficiaryResponseDto;
import com.bank.people.entity.Account;
import com.bank.people.entity.Bank;
import com.bank.people.entity.Beneficiary;
import com.bank.people.exception.BeneficiaryException;
import com.bank.people.repository.AccountRepository;
import com.bank.people.repository.BankRepository;
import com.bank.people.repository.BeneficiaryRepositroy;
import com.bank.people.util.BankConstants;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	BeneficiaryRepositroy beneficiaryRepository;

	@Autowired
	BankRepository bankRepository;

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

		if (customerAccount.get().getIbanNumber()
				.equals(beneficiaryRequestDto.getBeneficiaryIbanNumber())) {
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
