package com.oss.saber.dto.mapper;

import com.oss.saber.domain.Verification;
import com.oss.saber.dto.VerificationResponse;

public class VerificationMapper {
    public static VerificationResponse.infoResponse toResponse(Verification verification) {
        return VerificationResponse.infoResponse.builder()
                .id(verification.getId())
                .label(verification.getLabel())
                .result(verification.getResult())
                .build();
    }
}
