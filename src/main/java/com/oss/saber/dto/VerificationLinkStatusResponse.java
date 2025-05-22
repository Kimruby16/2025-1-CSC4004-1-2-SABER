package com.oss.saber.dto;

import com.oss.saber.domain.VerificationLinkStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class VerificationLinkStatusResponse {
    private VerificationLinkStatus status;
    private LocalDateTime startedAt;
}
