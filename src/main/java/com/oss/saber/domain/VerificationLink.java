package com.oss.saber.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "verification_links")
public class VerificationLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link_token", nullable = false, unique = true)
    private UUID linkToken;

    private String requirementText;
    private String additionalText;

    @Enumerated(EnumType.STRING)
    private VerificationLinkStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime expiresAt;

    private LocalDateTime termsAgreedAt;
    private LocalDateTime permissionsAgreedAt;

    private LocalDateTime lastActiveAt;

    @Enumerated(EnumType.STRING)
    private TerminatedReason terminatedReason;

    private LocalDateTime terminatedAt;

    private LocalDateTime firstAccessedAt;

    private String firstVisitorKey;

    @OneToMany(mappedBy = "verificationLink", cascade = CascadeType.ALL)
    private List<Verification> verifications;

    @OneToMany(mappedBy = "verificationLink", cascade = CascadeType.ALL)
    private List<Retry> retries;

    @OneToMany(mappedBy = "verificationLink", cascade = CascadeType.ALL)
    private List<AnomalyLog> anomalyLogs;
}
