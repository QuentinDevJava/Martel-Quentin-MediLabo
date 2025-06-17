package com.medilabo.authapi.dto;

import lombok.Data;

@Data
public class TokenAuth {
    String token;
    String username;

}
