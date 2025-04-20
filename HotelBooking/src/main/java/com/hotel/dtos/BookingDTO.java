package com.hotel.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.enums.BookingStatus;
import com.hotel.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

	private Long id;
	private UserDTO user;
	private RoomDTO room;
	private Long roomId;
	private PaymentStatus paymentStatus;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	
	private BigDecimal totalPrice;
	private String bookingReference;
	private LocalDate createdAt;
	
	private BookingStatus bookingStatus;	

}
