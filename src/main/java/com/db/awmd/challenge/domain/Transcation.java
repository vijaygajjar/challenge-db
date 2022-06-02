package com.db.awmd.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class Transcation {

	@NotNull
	@NotEmpty
	private final String fromAccountId;

	@NotNull
	@NotEmpty
	private final String toAccountId;

	@NotNull
	@Min(value = 0, message = "Requested Amount should be positive")
	private BigDecimal amuont;

	public Transcation(String fromAccountId, String toAccountId, BigDecimal amuont) {
			this.fromAccountId = fromAccountId;
			this.toAccountId = toAccountId;
			this.amuont = amuont;
		}

	public BigDecimal getAmuont() {
		return amuont;
	}

	public void setAmuont(BigDecimal amuont) {
		this.amuont = amuont;
	}

	public String getFromAccountId() {
		return fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	
}
