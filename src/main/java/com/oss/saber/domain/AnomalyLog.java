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
@Table(name = "anomaly_logs")
public class AnomalyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private VerificationLink verificationLink;

    private LocalDateTime detectedAt;

    @Enumerated(EnumType.STRING)
    private AnomalyLogType type;

    private String logs;

    private String deviceInfo;
    private String browserInfo;
}
