package com.oss.saber.repository;

import com.oss.saber.domain.VerificationLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationLinkRepository extends JpaRepository<VerificationLink, Long> {
    Optional<VerificationLink> findByLinkToken(UUID linkToken);
    Optional<VerificationLink> findById(Long verificationLinkId);
}
