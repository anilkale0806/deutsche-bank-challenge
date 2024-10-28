package com.dws.challenge.repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.Transaction;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.TransactionFailure;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository, TransactionRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();
	
	 @Autowired 
	 private JavaMailSender javaMailSender;
	 
	 @Value("${spring.mail.username}") 
	 private String sender;

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
		System.out.println("in map : " + accounts);
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	@Override
	public void doTransaction(Transaction transaction) throws TransactionFailure {
		String accountFromId = transaction.getAccountFromId();
		String accountToId = transaction.getAccountToId();
		BigDecimal amount = transaction.getTransferAmount();

		Account accountFrom = accounts.get(accountFromId);
		Account accountTo = accounts.get(accountToId);

		if (amount.compareTo(BigDecimal.ZERO) > 0 && accountFrom.getBalance().compareTo(amount) > 0) {

			synchronized (this) {
				BigDecimal remainingBalance = accountFrom.getBalance().subtract(amount);
				accountFrom.setBalance(remainingBalance);

				BigDecimal updatedBalance = accountTo.getBalance().add(amount);
				accountTo.setBalance(updatedBalance);
			}
			
			//EMAIL Notification
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			
			mailMessage.setFrom(sender);
	        mailMessage.setTo("abkale723@gmail.com");
	        mailMessage.setText("Dear Sir/Madam, I would like to inform you, Your saving account number" +accountFromId + "debited with amount Rs" + amount +" and its credited to account number" +accountTo+ "Kindly let me know if any further details are needed from bank side.");
	        mailMessage.setSubject("Transaction alert for your deutsche Bank..");
	        
	        javaMailSender.send(mailMessage);

		} else {
			throw new TransactionFailure("Account id " + accountFrom.getAccountId()
					+ " has negtive balance or transferred amount is greater than your balance!");
		}

	}

}
