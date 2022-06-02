package com.db.awmd.challenge.web;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transcation;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.TransactionsService;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.omg.IOP.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transactions")
@Slf4j
public class TransactionController {

  private final TransactionsService transactionsService;

  @Autowired
  public TransactionController(TransactionsService transactionsService) {
    this.transactionsService = transactionsService;
  }

  @PostMapping(path = "/transferBalance/from/{accountId}/to/{accountId}/amount/{amlount}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createAccount(@RequestBody @Valid Transcation transaction) {
    //log.info("Creating account {}", account);

    try {
    	this.transactionsService.transferAmout(transaction);
    	this.transactionsService.sendNotification(transaction);
    } catch (Exception daie) {
      return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

 
}
