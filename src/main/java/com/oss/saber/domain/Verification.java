package com.oss.saber.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @Setter
    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    @Setter
    private VerificationResult result;

    @Setter
    private String comment;

    @Setter
    private String videoUrl;

    @Setter
    private String imageUrl;
    
    private int reVerificationCount;

    public void addReVerification() {
        reVerificationCount++;
    }
}