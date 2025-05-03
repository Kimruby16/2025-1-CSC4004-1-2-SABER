package com.oss.saber.repository;

import com.oss.saber.domain.Retry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetryRepository extends JpaRepository<Retry, Long> {
    List<Retry> findByVerificationLinkId(Long verificationLinkId);
}