package com.hotel.services;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.hotel.entities.BookingReference;
import com.hotel.repositories.BookingReferenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingCodeGenerator {

	private final BookingReferenceRepository bookingReferenceRepository;
	
	public String generateBookingReference() {
		String bookingReference;
		do {
			bookingReference=generateRandomAlphaNumericCode(10);
		} while (isBookingReferenceExist(bookingReference));
		saveBookingReferenceToDatabase(bookingReference);
		return bookingReference;
	}

	private void saveBookingReferenceToDatabase(String bookingReference) {
		BookingReference newBookingReference=BookingReference.builder().referenceNo(bookingReference).build();
		bookingReferenceRepository.save(newBookingReference);
		
	}

	private boolean isBookingReferenceExist(String bookingReference) {
		return bookingReferenceRepository.findByReferenceNo(bookingReference).isPresent();
	}

	private String generateRandomAlphaNumericCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        Random random=new Random();
        StringBuilder stringBuilder=new StringBuilder(length);
        for (int i = 0; i < length; i++) {
			int index =random.nextInt(characters.length());
			stringBuilder.append(characters.charAt(index));
		}
		return stringBuilder.toString();
	}
}
