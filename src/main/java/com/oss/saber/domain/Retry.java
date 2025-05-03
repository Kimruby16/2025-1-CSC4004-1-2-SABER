package com.oss.saber.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "retries")
public class Retry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private VerificationLink verificationLink;

    private LocalDateTime requestedAt;
    private String reason;

    @Enumerated(EnumType.STRING)
    private RetryStatus status;

    private String retryVideoUrl;
}
