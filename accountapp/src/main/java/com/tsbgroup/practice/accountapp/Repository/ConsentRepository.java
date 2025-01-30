package com.tsbgroup.practice.accountapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsbgroup.practice.accountapp.Model.Consent;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long> {
    Optional<Consent> findByConsentId(String consentId);
}
