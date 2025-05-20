package com.oss.saber.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "verification_links")
public class VerificationLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @Column(name = "link_token", nullable = false, unique = true)
    private UUID linkToken;

    @Setter
    private String requirementText;

    private String additionalText;

    @Enumerated(EnumType.STRING)
    @Setter
    private VerificationLinkStatus status;

    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime startedAt;

    @Setter
    private LocalDateTime expiresAt;

    @Setter
    private LocalDateTime termsAgreedAt;

    @Setter
    private LocalDateTime permissionsAgreedAt;

    @Setter
    private LocalDateTime lastActiveAt;

    @Enumerated(EnumType.STRING)
    @Setter
    @Getter
    private TerminatedReason terminatedReason;

    @Setter
    private LocalDateTime terminatedAt;

    @Setter
    private LocalDateTime firstAccessedAt;

    @Setter
    private String firstVisitorKey;

    @Builder.Default
    @OneToMany(mappedBy = "verificationLink", cascade = CascadeType.ALL)
    private List<Verification> verifications = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "verificationLink", cascade = CascadeType.ALL)
    private List<Retry> retries = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "verificationLink", cascade = CascadeType.ALL)
    private List<AnomalyLog> anomalyLogs = new ArrayList<>();

    public void addVerification(Verification verification) {
        verifications.add(verification);
        verification.setVerificationLink(this); // 양방향 연관관계 유지
    }
}
