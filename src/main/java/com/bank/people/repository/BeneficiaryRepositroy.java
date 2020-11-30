package com.bank.people.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.people.entity.Beneficiary;

@Repository
public interface BeneficiaryRepositroy extends JpaRepository<Beneficiary, Integer> {

	Optional<Beneficiary> findByBeneficiaryIbanNumber(String beneficiaryIbanNumber);

	Optional<Beneficiary> findByCustomerIdAndBeneficiaryIbanNumber(Integer customerId, String beneficiaryIbanNumber);

}
