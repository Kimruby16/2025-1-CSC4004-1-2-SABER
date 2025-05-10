package com.oss.saber.controller;

import com.oss.saber.domain.VerificationLink;
import com.oss.saber.dto.CategorySettingRequest;
import com.oss.saber.dto.VerificationLinkResponse;
import com.oss.saber.dto.VerificationLinkSettingRequest;
import com.oss.saber.dto.VerificationLinkStatusResponse;
import com.oss.saber.dto.mapper.VerificationLinkStatusMapper;
import com.oss.saber.service.VerificationLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VerificationLinkRestController {

    @Value("${app.base-url}")
    private String baseUrl;

    private final VerificationLinkService verificationLinkService;

    @PostMapping("/verification/settings/category")
    public ResponseEntity<String> selectCategory(@RequestBody CategorySettingRequest request) {
        verificationLinkService.createLink(request.getCategoryId());
        return ResponseEntity.ok("카테고리 설정 완료");
    }

    @PostMapping("/verification/{verificationId}/settings")
    public ResponseEntity<String> setVerificationOptions(@PathVariable Long verificationId, @RequestBody VerificationLinkSettingRequest request) {
        verificationLinkService.settingVerificationLink(verificationId, request);
        return ResponseEntity.ok("상세 설정 완료");
    }

    @PostMapping("/verification/{verificationId}/link")
    public ResponseEntity<VerificationLinkResponse> createVerificationLink(@PathVariable Long verificationId) {
        VerificationLink link = verificationLinkService.getVerificationLink(verificationId);

        VerificationLinkResponse response = VerificationLinkResponse.builder()
                .link(baseUrl + "/saber?token=" + link.getLinkToken()) //링크 임의 설정, 추후에 다시 설정
                .status(link.getStatus())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verification/{verificationId}")
    public ResponseEntity<VerificationLinkStatusResponse> getVerificationStatus(@PathVariable Long verificationId) {
        VerificationLink verificationLink = verificationLinkService.getVerificationLink(verificationId);
        VerificationLinkStatusResponse response = VerificationLinkStatusMapper.toResponse(verificationLink);
        return ResponseEntity.ok(response);
    }
}