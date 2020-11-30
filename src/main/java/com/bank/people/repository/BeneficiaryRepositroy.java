package com.bank.people.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.people.entity.Beneficiary;

@Repository
public interface BeneficiaryRepositroy extends JpaRepository<Beneficiary, Integer>{

}
