package com.tsbgroup.practice.accountapp.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tsbgroup.practice.accountapp.Model.Consent;
import com.tsbgroup.practice.accountapp.Repository.ConsentRepository;

@DataJpaTest
public class ConsentRepositoryTest {

    @Autowired
    private ConsentRepository consentRepository;

    @Test
    void testSaveConsent() {
        Consent consent = new Consent();
        consent.setConsentId("urn-alphabank-intent-88379");
        consent.setUserId("12345");
        consent.setUserName("John Doe");
        
        Consent.Account account = new Consent.Account();
        account.setAccountId("67890");
        account.setAccountType("Savings");
        consent.setAccount(account);
        consent.setCreationDateTime(LocalDateTime.now());
        
        // save object
        Consent savedConsent = consentRepository.save(consent);

        // verify
        assertNotNull(savedConsent.getId());
        assertNotNull(savedConsent.getCreationDateTime());
    }
}
