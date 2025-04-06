package com.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hotel.enums.PaymentGateway;
import com.hotel.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String transactionId;
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	private PaymentGateway paymentGateway;
	
	private LocalDateTime paymentDate;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	private String bookingReference;
	private String failureReason;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}













