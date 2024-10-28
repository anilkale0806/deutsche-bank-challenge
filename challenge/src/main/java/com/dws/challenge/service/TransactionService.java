package com.dws.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dws.challenge.domain.Transaction;
import com.dws.challenge.repository.TransactionRepository;

import lombok.Getter;

@Service
public class TransactionService {

	@Getter
	private final TransactionRepository transactionRepository;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void doTransaction(Transaction transaction) {
		this.transactionRepository.doTransaction(transaction);
	}

}
