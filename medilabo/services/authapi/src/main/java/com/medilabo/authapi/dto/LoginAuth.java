package com.medilabo.authapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginAuth {
    private String username;
    private String password;
}