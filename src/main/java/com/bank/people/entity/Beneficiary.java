package com.bank.people.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class Beneficiary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer beneficiaryId;
	private String beneficiaryName;
	private Integer customerId;
	private Integer accountId;
	private String beneficiaryIbanNumber;

	public Integer getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Integer beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerid(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getBeneficiaryIbanNumber() {
		return beneficiaryIbanNumber;
	}

	public void setBeneficiaryIbanNumber(String beneficiaryIbanNumber) {
		this.beneficiaryIbanNumber = beneficiaryIbanNumber;
	}

}
