package com.oss.saber.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
