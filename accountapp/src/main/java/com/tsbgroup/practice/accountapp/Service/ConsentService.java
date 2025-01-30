package com.tsbgroup.practice.accountapp.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsbgroup.practice.accountapp.Model.Consent;
import com.tsbgroup.practice.accountapp.Repository.ConsentRepository;

@Service
public class ConsentService {

    @Autowired
    private ConsentRepository consentRepository;
    
    public ConsentService(ConsentRepository consentRepository) {
        this.consentRepository = consentRepository;
    }

    public Consent saveConsent(Consent consent) {
        consent.setCreationDateTime(LocalDateTime.now());
        consent.setExpirationDateTime(LocalDateTime.now().plusMonths(6));
        return consentRepository.save(consent);
    }

    public Optional<Consent> getConsentById(String consentId) {
        return consentRepository.findByConsentId(consentId);
    }

    public Optional<Consent> updateConsent(String consentId, String newUserName) {
        Optional<Consent> consentOptional = consentRepository.findByConsentId(consentId);
        if (consentOptional.isPresent()){
            Consent consent = consentOptional.get();
            consent.setUserName(newUserName);
            consentRepository.save(consent);
            return Optional.of(consent);
        }
        return Optional.empty();
    }
}
