package com.bank.people.dto;

import javax.validation.constraints.NotNull;

public class BeneficiaryRequestDto {
	@NotNull
	private String beneficiaryName;
	@NotNull
	private Integer customerId;
	private String beneficiaryIbanNumber;

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getBeneficiaryIbanNumber() {
		return beneficiaryIbanNumber;
	}

	public void setBeneficiaryIbanNumber(String beneficiaryIbanNumber) {
		this.beneficiaryIbanNumber = beneficiaryIbanNumber;
	}

}
