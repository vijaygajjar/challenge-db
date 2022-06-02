package com.db.awmd.challenge.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository extends CrudRepository<Account, String> {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);

  void clearAccounts();
  
  @Lock(LockModeType.PESSIMISTIC_WRITE) // To lock beofre hand to avoid multithread update
  @Transactional
  Account getAccountWIthLock(String accountId);
}
