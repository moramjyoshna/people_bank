package com.bank.people.dto;

public class UpdateBeneficiaryRequestDto {

	private String beneficiaryName;
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
	private Integer customerId;
	private String beneficiaryIbanNumber;
}
