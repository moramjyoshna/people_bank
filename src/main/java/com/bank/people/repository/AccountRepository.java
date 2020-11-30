package com.bank.people.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.people.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	public Optional<Account> findByAccountId(Integer accountId);
	

}
