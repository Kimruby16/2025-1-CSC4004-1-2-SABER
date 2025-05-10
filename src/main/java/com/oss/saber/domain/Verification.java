package com.oss.saber.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verifications")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label; //인증 방식

    @ManyToOne
    @JoinColumn(name = "link_id")
    @Setter
    private VerificationLink verificationLink;

    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    private VerificationResult result;

    private String comment;

    private String videoUrl;
    private String imageUrl;

    private int reVerificationCount;
}