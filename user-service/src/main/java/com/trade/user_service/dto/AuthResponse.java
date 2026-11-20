package com.trade.user_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.trade.user_service.entity.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	
	private String userId;
	private String emailId;
	private String firstName;
	private String lastName;
	private BigDecimal walletBalance;
	
	private String accessToken;
	private String refreshToken;
	private String tokenType = "Bearer";
}
