package com.kk.picturequizapi.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;
}
