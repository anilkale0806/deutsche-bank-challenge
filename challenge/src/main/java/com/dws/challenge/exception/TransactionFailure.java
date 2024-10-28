
package com.dws.challenge.exception;

public class TransactionFailure extends RuntimeException {

	private static final long serialVersionUID = -5635722743303969474L;

	public TransactionFailure(String message) {
		super(message);
	}
}
