package com.oss.saber.domain;

public enum VerificationLinkStatus {
    PENDING,          // 대기 상태
    IN_PROGRESS,      // 진행 중
    COMPLETED,        // 완료
    EXPIRED,          // 만료됨 (만료되었거나 타임아웃)
    TERMINATED        // 종료됨 (취소, 강제 종료, 종료된 상태 모두 포함)
}