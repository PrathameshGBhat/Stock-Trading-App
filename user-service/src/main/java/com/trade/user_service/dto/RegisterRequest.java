package com.trade.user_service.dto;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@Email(message = "Invalid email format !!!")
	@NotBlank(message = "Email is required !!!")
	private String email;
	
	@NotBlank(message = "Password is required !!!")
	@Size(min = 4, max = 14, message = "Password must be of size 6-14" )
	private String password;
	
	@NotBlank(message = "First Name is required !!!")
	private String firstName;
	
	@NotBlank(message = "Last Name is required !!!")
	private String lastName;
	
	private BigDecimal initialDeposit = BigDecimal.valueOf(10000);
}
