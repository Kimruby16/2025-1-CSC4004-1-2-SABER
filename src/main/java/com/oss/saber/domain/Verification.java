package com.oss.saber.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "verifications")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private VerificationLink verificationLink;

    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    private VerificationResult result;

    private String comment;
    private String videoUrl;
    private String imageUrl;

    private int reVerificationCount;
}