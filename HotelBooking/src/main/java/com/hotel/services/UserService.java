package com.hotel.services;

import com.hotel.dtos.LoginRequest;
import com.hotel.dtos.RegistrationRequest;
import com.hotel.dtos.Response;
import com.hotel.dtos.UserDTO;
import com.hotel.entities.User;

public interface UserService {
	Response registerUser(RegistrationRequest registrationRequest);
	Response loginUser(LoginRequest loginRequest);
	Response getAllUsers();
	Response getOwnAccountDetails();
	User getCurrentLoggedInUser();
	Response updateOwnAccount(UserDTO userDTO);
	Response deleteOwnAccount();
	Response getMyBookingHistory();
}
