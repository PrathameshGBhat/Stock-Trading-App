package com.trade.user_service.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trade.user_service.dto.AuthResponse;
import com.trade.user_service.dto.RegisterRequest;
import com.trade.user_service.entity.User;
import com.trade.user_service.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordencoder;

	private final JwtService jwtService;

	private final KafkaTemplate<String, Object> kafkaTemplate;

	private static final String USER_REGISTERED_TOPIC = "user.registered";

	public AuthResponse register(RegisterRequest request) {
		log.info("Registering User :{}" + request.getEmail());

		if (userRepository.existByEmail(request.getEmail())) {
			throw new RuntimeException("Email already Registered !!!" + request.getEmail());
		}

		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordencoder.encode(request.getPassword()));
		user.setWalletBalance(
				request.getInitialDeposit() != null ? request.getInitialDeposit() : BigDecimal.valueOf(10000));

		User userSaved = userRepository.save(user);

		log.info("User Registered :{}" + request.getEmail());

		// Putting data into Kafka Topic
		Map<String, Object> userRegisteredEvent = new HashMap<>();

		userRegisteredEvent.put("userId", userSaved.getId());
		userRegisteredEvent.put("firstName", userSaved.getFirstName());
		userRegisteredEvent.put("lastName", userSaved.getLastName());
		userRegisteredEvent.put("email", userSaved.getEmail());
		userRegisteredEvent.put("walletBalance", userSaved.getWalletBalance());

		kafkaTemplate.send(USER_REGISTERED_TOPIC, userSaved.getId(), userRegisteredEvent);

		String token = jwtService.generateAccessToken(userSaved.getId(), userSaved.getEmail());

		String refreshToken = jwtService.generateRefreshToken(userSaved.getId());

		return buildAuthResponse(userSaved, token, refreshToken);
	}

	private AuthResponse buildAuthResponse(User user, String token, String refreshToken) {

		return new AuthResponse(user.getId(), user.getEmail(), token, refreshToken);
	}
}
