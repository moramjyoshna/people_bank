package com.bank.people.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.people.dto.UpdateBeneficiaryRequestDto;
import com.bank.people.dto.UpdateBeneficiaryResponseDto;
import com.bank.people.entity.Beneficiary;
import com.bank.people.repository.BeneficiaryRepositroy;
import com.bank.people.util.BankConstants;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);

	@Autowired
	BeneficiaryRepositroy beneficiaryRepositroy;
	
	@Override
	public UpdateBeneficiaryResponseDto updateBeneficiary(UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto,
			Integer beneficiaryId) {
		logger.info(BankConstants.BENEFICIARY_UPDATE_SERVICE);
		UpdateBeneficiaryResponseDto updateBeneficiaryResponseDto = new UpdateBeneficiaryResponseDto();
		Optional<Beneficiary> beneficiary = beneficiaryRepositroy.findByBeneficiaryId(beneficiaryId);
		if(beneficiary.isPresent()){
			beneficiary.get().setBeneficiaryId(beneficiaryId);
			beneficiary.get().setBeneficiaryName(updateBeneficiaryRequestDto.getBeneficiaryName());
			beneficiary.get().setBeneficiaryIbanNumber(updateBeneficiaryRequestDto.getBeneficiaryIbanNumber());
			beneficiaryRepositroy.save(beneficiary.get());
		}else {
			logger.info(BankConstants.BENEFICIARYID_NOT_FOUND);
			//throw new InvalidBeneficiaryIdException(BankConstants.BENEFICIARY_ID_EXCEPTION);
		}
		updateBeneficiaryResponseDto.setMessage("Successfully added Beneficiary");
		updateBeneficiaryResponseDto.setStatusCode("200");
		return updateBeneficiaryResponseDto;
	}

}
