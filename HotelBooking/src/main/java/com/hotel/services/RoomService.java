package com.hotel.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hotel.dtos.Response;
import com.hotel.dtos.RoomDTO;
import com.hotel.enums.RoomType;

public interface RoomService {
	
	Response addRoom(RoomDTO roomDTO,MultipartFile imageFile);
	Response updateRoom(RoomDTO roomDTO,MultipartFile imageFile);
	Response getAllRooms();
	Response getRoomById(Long id);
	Response deleteRoom(Long id);
	Response getAvailableRooms(LocalDate checkInDate,LocalDate checkOutDate, RoomType roomType );
	List<RoomType> getAllRoomTypes();
	Response searchRoom(String input);
}
