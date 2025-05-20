package com.oss.saber.dto;

import com.oss.saber.domain.VerificationLinkStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


public class VerificationLinkResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class toResponse{
        Long id;
        String productName;
        String requirementText;
        String additionalText;
        List<VerificationResponse.infoResponse> verifications;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkResponse {
        String link;
        LocalDateTime expiresAt;
        VerificationLinkStatus status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationLinkSession{
        Long id;
        VerificationLinkStatus status;
        String productName;
        LocalDateTime expiresAt;
    }
}