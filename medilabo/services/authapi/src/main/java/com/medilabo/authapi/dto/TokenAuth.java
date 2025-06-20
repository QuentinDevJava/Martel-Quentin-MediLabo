package com.medilabo.authapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenAuth {
    String token;
    String username;
}
