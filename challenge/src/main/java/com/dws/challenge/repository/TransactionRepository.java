package com.dws.challenge.repository;

import com.dws.challenge.domain.Transaction;
import com.dws.challenge.exception.TransactionFailure;

public interface TransactionRepository {

	void doTransaction(Transaction transaction)throws TransactionFailure;
}
