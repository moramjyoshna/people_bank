package com.bank.people.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.people.entity.Account;
import com.bank.people.entity.Beneficiary;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	Account findByIbanNumber(String ibanNumber);

	Optional<Account> findByCustomerId(Integer customerId);

	public Optional<Account> findByAccountId(Integer accountId);

}
