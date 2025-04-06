package com.hotel.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.enums.PaymentGateway;
import com.hotel.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {

	private Long id;
    private BookingDTO booking;
	private String transactionId;
	private	BigDecimal amount;
	private	PaymentGateway paymentMethod;
	private	LocalDateTime paymentDate;
	private	PaymentStatus status;
	private String bookingReference;
	private	String failureReason;
	private	String approvalLink;
}
