package com.bank.people.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.people.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
	
	Optional<Bank> findByBankCode(Integer code);

}
