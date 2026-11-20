package com.trade.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@Email(message = "Invalid email format !!!")
	@NotBlank(message = "Email is required !!!")
	private String emailId;
	
	@NotBlank(message = "Password is required !!!")
	@Size(min = 4, max = 14, message = "Password must be of size 6-14" )
	private String password;
}
