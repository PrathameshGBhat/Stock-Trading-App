package com.trade.user_service.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trade.user_service.dto.AuthResponse;
import com.trade.user_service.dto.LoginRequest;
import com.trade.user_service.dto.RegisterRequest;
import com.trade.user_service.dto.UserResponse;
import com.trade.user_service.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(userService.login(request));
	}
	
	@GetMapping("/me")
	public ResponseEntity<UserResponse> getMyProfile(@RequestHeader("X-User-Id") String userId) {
		return ResponseEntity.ok(userService.getById(userId));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getById(@PathVariable String userId) {
		return ResponseEntity.ok(userService.getById(userId));
	}
	
	@PostMapping("/{userId}/funds/add")
	public ResponseEntity<UserService> addFunds(@PathVariable String userId, @RequestParam BigDecimal amount){
		return ResponseEntity.ok(userService.addFunds(userId, amount));
	}
	
	@PostMapping("/{userId}/funds/deduct")
	public ResponseEntity<UserService> deductFunds(@PathVariable String userId, @RequestParam BigDecimal amount){
		return ResponseEntity.ok(userService.deductFunds(userId, amount));
	}
	
	@PostMapping("/{userId}/funds/credit")
	public ResponseEntity<UserService> creditFunds(@PathVariable String userId, @RequestParam BigDecimal amount){
		return ResponseEntity.ok(userService.creditFunds(userId, amount));
	}
}
