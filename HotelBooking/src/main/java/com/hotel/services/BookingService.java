package com.hotel.services;

import com.hotel.dtos.BookingDTO;
import com.hotel.dtos.Response;

public interface BookingService {

	Response getAllBookings();
	Response createBooking(BookingDTO bookingDTO);
	Response findBookingByReferenceNo(String bookingReference);
	Response updateBooking(BookingDTO bookingDTO);
}
