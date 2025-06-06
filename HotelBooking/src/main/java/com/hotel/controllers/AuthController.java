package com.hotel.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.dtos.LoginRequest;
import com.hotel.dtos.RegistrationRequest;
import com.hotel.dtos.Response;
import com.hotel.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@RequestBody @Valid RegistrationRequest request) {
		return ResponseEntity.ok(userService.registerUser(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@RequestBody @Valid LoginRequest request) {
		return ResponseEntity.ok(userService.loginUser(request));
	}
	
}







