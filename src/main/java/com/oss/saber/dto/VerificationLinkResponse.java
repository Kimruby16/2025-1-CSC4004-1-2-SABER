package com.oss.saber.dto;

import com.oss.saber.domain.VerificationLinkStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Setter
@Getter
public class VerificationLinkResponse {
    private String link;
    private LocalDateTime expiresAt;
    private VerificationLinkStatus status;
}