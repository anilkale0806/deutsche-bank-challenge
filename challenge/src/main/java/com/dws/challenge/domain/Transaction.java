package com.dws.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Transaction {

	@NotNull
	@NotEmpty
	private final String accountFromId;

	@NotNull
	@NotEmpty
	private final String accountToId;

	@NotNull
	@Min(value = 0, message = "Initial balance must be positive.")
	private BigDecimal transferAmount;
	
	@JsonCreator
	public Transaction(@JsonProperty("accountFromId") String accountFromId,
			@JsonProperty("accountToId") String accountToId,
			@JsonProperty("transferAmount") BigDecimal transferAmount) {
		this.accountFromId = accountFromId;
		this.accountToId = accountToId;
		this.transferAmount = transferAmount;
	}

}
