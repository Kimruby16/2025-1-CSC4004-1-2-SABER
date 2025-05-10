package com.oss.saber.service;

import com.oss.saber.domain.*;
import com.oss.saber.dto.VerificationLinkResponse;
import com.oss.saber.dto.VerificationLinkSettingRequest;
import com.oss.saber.repository.CategoryRepository;
import com.oss.saber.repository.VerificationLinkRepository;
import com.oss.saber.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.oss.saber.domain.VerificationResult.PENDING;

@Service
@RequiredArgsConstructor
public class VerificationLinkService {
    private final VerificationLinkRepository verificationLinkRepository;
    private final CategoryRepository categoryRepository;
    private final VerificationRepository verificationRepository;

    public VerificationLink createLink(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        VerificationLink link = VerificationLink.builder()
                .linkToken(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .status(VerificationLinkStatus.PENDING)
                .termsAgreedAt(LocalDateTime.now())
                .permissionsAgreedAt(LocalDateTime.now())
                .build();

        for (DefaultVerification defaultVerification : category.getDefaultVerifications()) {
            Verification verification = Verification.builder()
                    .label(defaultVerification.getContent())
                    .result(PENDING)
                    .build();
            link.addVerification(verification);
        }

        return verificationLinkRepository.save(link); // 연관된 Verification도 함께 저장
    }

    public VerificationLink settingVerificationLink(Long verificationLinkId, VerificationLinkSettingRequest request) {
        VerificationLink verificationLink = verificationLinkRepository.findById(verificationLinkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 인증 링크가 존재하지 않습니다."));

        // 제한 시간 설정
        verificationLink.setExpiresAt(LocalDateTime.now().plusMinutes(request.getLimitedMinutes()));

        // 사용자 지정 요청 저장
        verificationLink.setRequirementText(request.getCustomRequests());

        // 인증 방식 설정
        List<Integer> customOptions = request.getVerificationMethods();
        for (Integer methodId : customOptions) {
            switch (methodId) {
                case 1: // 사진
                    verificationLink.addVerification(
                            Verification.builder()
                                    .label("사진 촬영")
                                    .result(VerificationResult.PENDING)
                                    .build()
                    );
                    break;
                case 2: // 동영상
                    verificationLink.addVerification(
                            Verification.builder()
                                    .label("동영상 촬영")
                                    .result(VerificationResult.PENDING)
                                    .build()
                    );
                    break;
                // 필요 시 추가 case 가능
                default:
                    throw new IllegalArgumentException("지원하지 않는 인증 방식입니다: " + methodId);
            }
        }
        return verificationLinkRepository.save(verificationLink);
    }

    // 링크 시작 시간 기록
    public void markStarted(Long verificationLinkId) {
        VerificationLink verificationLink = verificationLinkRepository.findById(verificationLinkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 인증 링크가 존재하지 않습니다."));
        verificationLink.setStartedAt(LocalDateTime.now());
        verificationLink.setFirstAccessedAt(LocalDateTime.now());
        verificationLink.setLastActiveAt(LocalDateTime.now());
        verificationLinkRepository.save(verificationLink);
    }

    public VerificationLink getVerificationLink(Long verificationLinkId) {
        return verificationLinkRepository.findById(verificationLinkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 인증 링크가 존재하지 않습니다."));
    }

    // 링크 종료 처리
    public void terminateLink(Long verificationLinkId, TerminatedReason reason) {
        VerificationLink verificationLink = verificationLinkRepository.findById(verificationLinkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 인증 링크가 존재하지 않습니다."));

        verificationLink.setTerminatedAt(LocalDateTime.now());
        verificationLink.setTerminatedReason(reason);
        verificationLink.setStatus(VerificationLinkStatus.TERMINATED); // 종료 상태로 설정
        verificationLinkRepository.save(verificationLink);
    }

    // 링크 만료 처리
    public void expireLink(Long verificationLinkId) {
        VerificationLink verificationLink = verificationLinkRepository.findById(verificationLinkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 인증 링크가 존재하지 않습니다."));

        verificationLink.setExpiresAt(LocalDateTime.now());
        verificationLink.setStatus(VerificationLinkStatus.EXPIRED); // 만료 상태로 설정
        verificationLinkRepository.save(verificationLink);
    }

    // 마지막 활동 시간 갱신
    public void updateLastActive(Long verificationLinkId) {
        VerificationLink verificationLink = verificationLinkRepository.findById(verificationLinkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 인증 링크가 존재하지 않습니다."));

        verificationLink.setLastActiveAt(LocalDateTime.now());
        verificationLinkRepository.save(verificationLink);
    }

    // 링크 상태 갱신 (예: 유효성 검사 후)
    public void updateLinkStatus(Long verificationLinkId, VerificationLinkStatus status) {
        VerificationLink verificationLink = verificationLinkRepository.findById(verificationLinkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 인증 링크가 존재하지 않습니다."));

        verificationLink.setStatus(status);
        verificationLinkRepository.save(verificationLink);
    }
}