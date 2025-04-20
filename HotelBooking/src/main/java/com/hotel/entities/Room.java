package com.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hotel.enums.RoomType;
import com.hotel.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Min(value = 1,message = "Room Number must be at least 1")
	@Column(unique = true)
	private Integer roomNumber;
	
	@Enumerated(EnumType.STRING)
	private RoomType type;
	
	@DecimalMin(value = "0.1",message = "Price per night is required")
	private BigDecimal pricePerNight;
	
	@Min(value = 1,message = "capacity must be at least 1")
	private Integer capacity;
	
	private String description;
	private String imageUrl;
}




























