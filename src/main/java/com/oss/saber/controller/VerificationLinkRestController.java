package com.oss.saber.controller;

import com.oss.saber.domain.VerificationLink;
import com.oss.saber.dto.CategorySettingRequest;
import com.oss.saber.dto.VerificationLinkResponse;
import com.oss.saber.dto.VerificationLinkSettingRequest;
import com.oss.saber.dto.VerificationLinkStatusResponse;
import com.oss.saber.dto.mapper.VerificationLinkStatusMapper;
import com.oss.saber.service.VerificationLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "인증 링크 API", description = "")
public class VerificationLinkRestController {

    @Value("${app.base-url}")
    private String baseUrl;

    private final VerificationLinkService verificationLinkService;

    @PostMapping("/verification/settings/category")
    @Operation(summary = "카테고리 선택", description = "품목의 카테고리를 선택합니다.")
    public ResponseEntity<Long> selectCategory(@RequestBody CategorySettingRequest request) {
        VerificationLink link = verificationLinkService.createLink(request.getCategoryId());
        return ResponseEntity.ok(link.getId());
    }

    @PostMapping("/verification/{verificationId}/settings")
    @Operation(summary = "상세 인증 방식 선택", description = "원하는 인증 방식을 설정합니다. 시간, 방법 등")
    public ResponseEntity<String> setVerificationOptions(@PathVariable Long verificationId, @RequestBody VerificationLinkSettingRequest request) {
        verificationLinkService.settingVerificationLink(verificationId, request);
        return ResponseEntity.ok("상세 설정 완료");
    }

    @PostMapping("/verification/{verificationId}/link")
    @Operation(summary = "인증 링크 생성", description = "인증이 진행될 링크를 생성합니다.")
    public ResponseEntity<VerificationLinkResponse> createVerificationLink(@PathVariable Long verificationId) {
        VerificationLink link = verificationLinkService.getVerificationLink(verificationId);

        VerificationLinkResponse response = VerificationLinkResponse.builder()
                .link(baseUrl + "/saber?token=" + link.getLinkToken()) //링크 임의 설정, 추후에 다시 설정
                .status(link.getStatus())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verification/{verificationId}")
    @Operation(summary = "인증 진행 상태 조회", description = "인증 진행 상태를 조회합니다.")
    public ResponseEntity<VerificationLinkStatusResponse> getVerificationStatus(@PathVariable Long verificationId) {
        VerificationLink verificationLink = verificationLinkService.getVerificationLink(verificationId);
        VerificationLinkStatusResponse response = VerificationLinkStatusMapper.toResponse(verificationLink);
        return ResponseEntity.ok(response);
    }
}