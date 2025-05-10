package com.oss.saber.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DefaultVerificationResponse {
    private String verificationLabel;
}