package com.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.hotel.dtos.NotificationDTO;
import com.hotel.notification.NotificationService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableAsync
@RequiredArgsConstructor
public class HotelBookingApplication {
	
	private final NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingApplication.class, args);
	}
	
	@PostConstruct
	public void sendDummyEmail() {
		NotificationDTO notificationDTO=NotificationDTO.builder()
				.recipient(("channoufisaber@gmail.com")).subject("salem").body("ssss").build();
				
				notificationService.sendEmail(notificationDTO);
	}

}
