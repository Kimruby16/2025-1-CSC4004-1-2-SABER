package com.oss.saber.dto;

import lombok.Getter;

import java.util.UUID;

public class VerificationLinkRequest {

    @Getter
    public static class SessionRequest {
        String visitorKey;
    }
}
