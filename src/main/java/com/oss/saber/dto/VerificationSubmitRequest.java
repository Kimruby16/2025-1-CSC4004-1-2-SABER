package com.oss.saber.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class VerificationSubmitRequest {
    private String fileUrl;

    private String comment;
}
