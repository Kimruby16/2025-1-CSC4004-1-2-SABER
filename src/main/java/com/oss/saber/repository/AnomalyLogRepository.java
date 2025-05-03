package com.oss.saber.repository;

import com.oss.saber.domain.AnomalyLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnomalyLogRepository extends JpaRepository<AnomalyLog, Long> {
    List<AnomalyLog> findByVerificationLinkId(Long verificationLinkId);
}
