package com.oss.saber.dto.mapper;

import com.oss.saber.domain.VerificationLink;
import com.oss.saber.dto.VerificationLinkStatusResponse;

public class VerificationLinkStatusMapper {

    public static VerificationLinkStatusResponse toResponse(VerificationLink verificationLink) {
        return VerificationLinkStatusResponse.builder()
                .status(verificationLink.getStatus())
                .startedAt(verificationLink.getStartedAt())
                .build();
    }
}