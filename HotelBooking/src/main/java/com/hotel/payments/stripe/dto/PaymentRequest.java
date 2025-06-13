package com.hotel.payments.stripe.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequest {

	@NotBlank(message = "Booking is required")
	private String bookingReference;
	private BigDecimal amount;
	
	private String transactionId;
	private boolean success;
	private String failureReason;

	
}
