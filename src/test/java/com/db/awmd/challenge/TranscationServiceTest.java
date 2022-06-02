package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transcation;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.TransactionsService;

import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranscationServiceTest {

  @Autowired
  private TransactionsService transactionsService;
  
  @Autowired
  private AccountsService accountsService;

  @Test
  public void transferAmount() throws Exception {
System.out.println("tepm");
    Account fromAccount = new Account("Id-123");
    fromAccount.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(fromAccount);

    assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(fromAccount);
    
    Account toAccount = new Account("Id-111");
    toAccount.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(toAccount);

    assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(toAccount);
    
    Transcation dtoTranscation = new Transcation(fromAccount.getAccountId(), toAccount.getAccountId(), new BigDecimal(1000));
    
    transactionsService.transferAmout(dtoTranscation);
  }

}
