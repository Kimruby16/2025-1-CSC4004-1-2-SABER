package com.oss.saber.dto;

import com.oss.saber.domain.VerificationResult;
import lombok.Builder;
import lombok.Getter;

public class VerificationResponse {

    @Getter
    @Builder
    public static class infoResponse {
        Long id;
        String label;
        VerificationResult result;
    }
}
