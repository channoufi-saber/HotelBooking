package com.hotel.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.dtos.BookingDTO;
import com.hotel.dtos.LoginRequest;
import com.hotel.dtos.RegistrationRequest;
import com.hotel.dtos.Response;
import com.hotel.dtos.UserDTO;
import com.hotel.entities.Booking;
import com.hotel.entities.User;
import com.hotel.enums.UserRole;
import com.hotel.exceptions.InvalidCredentialException;
import com.hotel.exceptions.NotFoundException;
import com.hotel.repositories.BookingRepository;
import com.hotel.repositories.UserRepository;
import com.hotel.security.JwtUtils;
import com.hotel.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final ModelMapper modelMapper;
	private final BookingRepository bookingRepository;

	@Override
	public Response registerUser(RegistrationRequest registrationRequest) {
		UserRole role = UserRole.CUSTOMER;
		if (registrationRequest.getRole() != null) {
			role = registrationRequest.getRole();
		}

		User userToSave = User.builder().firstName(registrationRequest.getFirstName())
				.lastName(registrationRequest.getLastName()).email(registrationRequest.getEmail())
				.password(passwordEncoder.encode(registrationRequest.getPassword()))
				.phoneNumber(registrationRequest.getPhoneNumber()).role(role).active(Boolean.TRUE).build();

		userRepository.save(userToSave);

		return Response.builder().status(200).message("user created successfully").build();
	}

	@Override
	public Response loginUser(LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new NotFoundException("Email Not Found"));
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new InvalidCredentialException("Password doesn't match");
		}
		String token = jwtUtils.generateToken(user.getEmail());
		return Response.builder().status(200).message("user logged in successfully").role(user.getRole()).token(token)
				.active(user.isActive()).expirationTime("6 months").build();
	}

	@Override
	public Response getAllUsers() {
		List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		List<UserDTO> userDTOList = modelMapper.map(users, new TypeToken<List<UserDTO>>() {
		}.getType());
		return Response.builder().status(200).message("success").users(userDTOList).build();
	}

	@Override
	public Response getOwnAccountDetails() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return Response.builder().status(200).message("success").user(userDTO).build();
	}

	@Override
	public User getCurrentLoggedInUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));
	}

	@Override
	public Response updateOwnAccount(UserDTO userDTO) {
		User existingUser = getCurrentLoggedInUser();
		if (userDTO.getEmail() != null)
			existingUser.setEmail(userDTO.getEmail());
		if (userDTO.getFirstName() != null)
			existingUser.setFirstName(userDTO.getFirstName());
		if (userDTO.getLastName() != null)
			existingUser.setLastName(userDTO.getLastName());
		if (userDTO.getPhoneNumber() != null)
			existingUser.setPhoneNumber(userDTO.getPhoneNumber());
		if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
			existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}
		userRepository.save(existingUser);
		return Response.builder().status(200).message("User Updated Successfully").build();
	}

	@Override
	public Response deleteOwnAccount() {
		User existingUser = getCurrentLoggedInUser();
		userRepository.delete(existingUser);
		return Response.builder().status(200).message("User Deleted Successfully").build();
	}

	@Override
	public Response getMyBookingHistory() {
		User currentUser = getCurrentLoggedInUser();
		List<Booking> bookingList = bookingRepository.findByUserId(currentUser.getId());
		List<BookingDTO> bookingDTOList = modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>() {
		}.getType());
		return Response.builder().status(200).message("success").bookings(bookingDTOList).build();
	}

}
