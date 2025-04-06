package com.hotel.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hotel.entities.User;
import com.hotel.exceptions.NotFoundException;
import com.hotel.repositories.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository = null;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(username).orElseThrow(()->new NotFoundException("user Not Found"));
		
		return AuthUser.builder().user(user).build();
	}

}
