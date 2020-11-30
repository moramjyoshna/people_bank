package com.bank.people.repository;

import java.util.Optional;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.people.entity.Beneficiary;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {

	Optional<Beneficiary> findByBeneficiaryId(Integer beneficiaryId);
	
	Optional<List<Beneficiary>> findByCustomerId(Integer customerId,Pageable pagable);
}
