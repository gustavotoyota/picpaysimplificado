package com.picpaysimplificado.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;

@Service
public class TransactionService {
  @Autowired
  private UserService userService;
  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private NotificationService notificationService;

  public Transaction createTransaction(TransactionDTO transaction) throws Exception {
    User sender = this.userService.findUserById(transaction.senderId());
    User receiver = this.userService.findUserById(transaction.receiverId());

    this.userService.validateTransaction(sender, transaction.amount());

    boolean isAuthorized = this.authorizeTransaction(sender, transaction.amount());
    if (!isAuthorized) {
      throw new Exception("Transação não autorizada");
    }

    Transaction newTransaction = new Transaction();

    newTransaction.setAmount(transaction.amount());
    newTransaction.setSender(sender);
    newTransaction.setReceiver(receiver);
    newTransaction.setTimestamp(LocalDateTime.now()); 

    sender.setBalance(sender.getBalance().subtract(transaction.amount()));
    receiver.setBalance(receiver.getBalance().add(transaction.amount()));

    this.userService.saveUser(sender);
    this.transactionRepository.save(newTransaction);

    this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
    this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

    return newTransaction;
  }

  public boolean authorizeTransaction(User sender, BigDecimal amount) {
    ResponseEntity<Map> authorizationResponse = this.restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

    if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
      String message = (String)authorizationResponse.getBody().get("message");
      
      return message.equalsIgnoreCase("Autorizado");
    } else {
      return false;
    }
  }
}
