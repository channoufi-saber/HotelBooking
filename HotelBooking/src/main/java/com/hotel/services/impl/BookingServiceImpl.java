package com.hotel.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotel.dtos.BookingDTO;
import com.hotel.dtos.NotificationDTO;
import com.hotel.dtos.Response;
import com.hotel.entities.Booking;
import com.hotel.entities.Room;
import com.hotel.entities.User;
import com.hotel.enums.BookingStatus;
import com.hotel.enums.PaymentStatus;
import com.hotel.exceptions.InvalidBookingStateAndDateException;
import com.hotel.exceptions.NotFoundException;
import com.hotel.notification.NotificationService;
import com.hotel.repositories.BookingRepository;
import com.hotel.repositories.RoomRepository;
import com.hotel.services.BookingCodeGenerator;
import com.hotel.services.BookingService;
import com.hotel.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
	
	private final BookingRepository bookingRepository;
	private final RoomRepository roomRepository;
	private final NotificationService notificationService;
	private final ModelMapper modelMapper;
	private final UserService userService;
	private final BookingCodeGenerator bookingCodeGenerator;
	
	@Override
	public Response getAllBookings() {
		List<Booking> bookingList=bookingRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
		List<BookingDTO> bookingDTOList=modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>() {}.getType());
		for (BookingDTO bookingDTO : bookingDTOList) {
			bookingDTO.setUser(null);
			bookingDTO.setRoom(null);
		}
		return Response.builder().status(200).message("success").bookings(bookingDTOList).build();
	}

	@Override
	public Response createBooking(BookingDTO bookingDTO) {
		User currentUser=userService.getCurrentLoggedInUser();
		Room room=roomRepository.findById(bookingDTO.getRoomId()).orElseThrow(() -> new NotFoundException("Room Not Found"));
		
		if (bookingDTO.getCheckInDate().isBefore(LocalDate.now())) {
			throw new InvalidBookingStateAndDateException("check in date cannot be before today");
		}
		
		if (bookingDTO.getCheckOutDate().isBefore(bookingDTO.getCheckInDate())) {
			throw new InvalidBookingStateAndDateException("check out date cannot be before check in date");
		}
		
		if (bookingDTO.getCheckInDate().isEqual(bookingDTO.getCheckOutDate())) {
			throw new InvalidBookingStateAndDateException("check in date cannot be equal check out date");
		}
		
		boolean isAvailable=bookingRepository.isRoomAvailable(room.getId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
		if (!isAvailable) {
			throw new InvalidBookingStateAndDateException("Room is not available for the selected date ranges");
		}
		BigDecimal totalPrice=calculateTotalPrice(room,bookingDTO);
		String bookingReference=bookingCodeGenerator.generateBookingReference();
		
		Booking booking =new Booking();
		booking.setUser(currentUser);
		booking.setRoom(room);
		booking.setCheckInDate(bookingDTO.getCheckInDate());
		booking.setCheckOutDate(bookingDTO.getCheckOutDate());
		booking.setTotalPrice(totalPrice);
		booking.setBookingReference(bookingReference);
		booking.setBookingStatus(BookingStatus.BOOKED);
		booking.setPaymentStatus(PaymentStatus.PENDING);
		booking.setCreatedAt(LocalDate.now());
		
		bookingRepository.save(booking);
		
        String paymentUrl = "http://localhost:3000/payment/" + bookingReference + "/" + totalPrice;
        log.info("PAYMENT LINK/ {}",paymentUrl);
        
        NotificationDTO notificationDTO=NotificationDTO.builder().recipient(currentUser.getEmail())
        		.subject("Booking Confirmation")
        		.body(String.format("Your booking has been created successfully. Please proceed with your payment using the payment link below \n%s", paymentUrl))
        		.bookingReference(bookingReference).build();
        
        notificationService.sendEmail(notificationDTO);
        
		return Response.builder().status(200).message("Booking is successfully").booking(bookingDTO).build();
	}



	@Override
	public Response findBookingByReferenceNo(String bookingReference) {
		Booking booking=bookingRepository.findByBookingReference(bookingReference)
				.orElseThrow(()-> new NotFoundException("Booking with reference No: "+ bookingReference+ "Not found"));
				
		BookingDTO bookingDTO=modelMapper.map(booking, BookingDTO.class);		
		return Response.builder().status(200).message("success").booking(bookingDTO).build();
	}

	@Override
	public Response updateBooking(BookingDTO bookingDTO) {
		if (bookingDTO.getId() == null) {
			throw new NotFoundException("Booking id is required");
		}
		Booking existingBooking=bookingRepository.findById(bookingDTO.getId())
				.orElseThrow(()-> new NotFoundException("Booking Not Found"));
		
		if (bookingDTO.getBookingStatus() != null) {
			existingBooking.setBookingStatus(bookingDTO.getBookingStatus());
		}
		
		if (bookingDTO.getPaymentStatus() != null) {
			existingBooking.setPaymentStatus(bookingDTO.getPaymentStatus());
		}
		
		bookingRepository.save(existingBooking);
		
		return Response.builder().status(200).message("Booking Updated successfully").build();
	}
	
	private BigDecimal calculateTotalPrice(Room room, BookingDTO bookingDTO) {
		BigDecimal pricePerNight=room.getPricePerNight();
		long days=ChronoUnit.DAYS.between(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
		return pricePerNight.multiply(BigDecimal.valueOf(days));
	}

}




























