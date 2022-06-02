package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transcation;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Thrown;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransactionsService {

  @Getter
  private final AccountsRepository accountsRepository;
  
  @Getter
  private final NotificationService notificationService;

  @Autowired
  public TransactionsService(AccountsRepository accountsRepository, NotificationService notificationService) {
    this.accountsRepository = accountsRepository;
	this.notificationService = notificationService;
  }

  @Transactional
  public void transferAmout(@Valid Transcation transaction) throws Exception {
	String fromAccountId = transaction.getFromAccountId();
	String toAccountId = transaction.getFromAccountId();
	BigDecimal amount = transaction.getAmuont();
	
	Account fromAccount = accountsRepository.getAccountWIthLock(fromAccountId);
	
	if(fromAccount==null) {
		throw new Exception("Account does not exists");
	}
	
	Account toAccount = accountsRepository.getAccountWIthLock(toAccountId);
	
	if(toAccount==null) {
		throw new Exception("Account does not exists");
	}
	
	BigDecimal fromBalance = fromAccount.getBalance();
	BigDecimal updatedFromBalance = fromBalance.subtract(amount);
	
	if(updatedFromBalance.compareTo(BigDecimal.ZERO)<0) {
		throw new Exception("Balance cannot be negative");
	}
	
	fromAccount.setBalance(updatedFromBalance);
	
	BigDecimal toBalance = toAccount.getBalance();
	BigDecimal updatedToBalance = toBalance.add(amount);
	
	toAccount.setBalance(updatedToBalance);
	List<Account> lstAccounts = new ArrayList<>();
	lstAccounts.add(fromAccount);
	lstAccounts.add(toAccount);
	
	accountsRepository.saveAll(lstAccounts);
	
  }
  
  public void sendNotification(@Valid Transcation transaction) throws Exception {
	  Account fromAccount = accountsRepository.getAccount(transaction.getFromAccountId());
	  Account toAccount = accountsRepository.getAccount(transaction.getToAccountId());
	  notificationService.notifyAboutTransfer(fromAccount, "INR " + transaction.getAmuont() + " is transferd from your account" + transaction.getFromAccountId() + "to account:" + transaction.getToAccountId());
	  notificationService.notifyAboutTransfer(toAccount, "INR " + transaction.getAmuont() + " is transferd to your account" + transaction.getToAccountId() + "from account:" + transaction.getFromAccountId());
  }
  
}
