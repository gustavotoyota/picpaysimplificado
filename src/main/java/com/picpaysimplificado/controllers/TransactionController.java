package com.picpaysimplificado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.services.TransactionService;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
  @Autowired
  private TransactionService transactionService;

  @PostMapping
  public ResponseEntity<Transaction> create(@RequestBody TransactionDTO transactionDTO) throws Exception {
    Transaction transaction = this.transactionService.createTransaction(transactionDTO);

    return new ResponseEntity<Transaction>(transaction, HttpStatus.CREATED);
  }
}
