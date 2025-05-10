package com.oss.saber.dto;

import com.oss.saber.domain.VerificationLinkStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VerificationLinkStatusResponse {
    private VerificationLinkStatus status;
}
