package com.oss.saber.controller;

import com.oss.saber.domain.Verification;
import com.oss.saber.domain.VerificationLink;
import com.oss.saber.dto.VerificationLinkResponse;
import com.oss.saber.dto.VerificationResponse;
import com.oss.saber.dto.mapper.VerificationMapper;
import com.oss.saber.service.VerificationLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "인증 API (판매자측)", description = "")
public class VerificationLink2RestController {

    private final VerificationLinkService verificationLinkService;

    @GetMapping("/saber")
    @Operation(summary = "판매자 측 인증 시작", description = "인증을 시작합니다.")
    public ResponseEntity<VerificationLinkResponse.VerificationLinkSession> initVerification(@RequestParam("token") UUID token) {
        VerificationLink link = verificationLinkService.initVerification(token);
        VerificationLinkResponse.VerificationLinkSession response = VerificationLinkResponse.VerificationLinkSession.builder()
                .id(link.getId())
                .status(link.getStatus())
                .productName(link.getProductName())
                .expiresAt(link.getExpiresAt())
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/verification/{verificationId}/agree")
    @Operation(summary = "동의 여부 제출", description = "")
    public ResponseEntity<String> agreeToTerms(@PathVariable Long verificationId) {
        verificationLinkService.agreeTerms(verificationId);
        return ResponseEntity.ok("동의 완료");
    }

    @GetMapping("/verification/{verificationLinkId}/info")
    @Operation(summary = "인증 내용 조회", description = "인증에 대한 내용을 제공합니다.")
    public ResponseEntity<VerificationLinkResponse.toResponse> getVerificationLink(@PathVariable Long verificationLinkId) {
        VerificationLink link = verificationLinkService.getVerificationLink(verificationLinkId);

        List<Verification> verifications = link.getVerifications();
        List<VerificationResponse.infoResponse> verificationResponses = verifications.stream()
                .map(VerificationMapper::toResponse).collect(Collectors.toList());

        VerificationLinkResponse.toResponse response = VerificationLinkResponse.toResponse.builder()
                .id(link.getId())
                .additionalText(link.getAdditionalText())
                .requirementText(link.getRequirementText())
                .productName(link.getProductName())
                .verifications(verificationResponses)
                .build();

        return ResponseEntity.ok(response);
    }
}