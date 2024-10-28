package com.dws.challenge.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dws.challenge.domain.Transaction;
import com.dws.challenge.exception.TransactionFailure;
import com.dws.challenge.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/accounts/transfer")
@Slf4j
public class TransactionController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> transferAmount(@RequestBody @Valid Transaction transaction) {
		log.info("Transfer amount {}", transaction);

		try {
			this.transactionService.doTransaction(transaction);
		} catch (TransactionFailure transactionFailure) {
			return new ResponseEntity<>(transactionFailure.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
