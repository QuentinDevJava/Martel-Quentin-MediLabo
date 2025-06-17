package com.medilabo.authapi.dto;

import lombok.Data;

@Data
public class LoginAuth {
	private String username;
	private String password;
}