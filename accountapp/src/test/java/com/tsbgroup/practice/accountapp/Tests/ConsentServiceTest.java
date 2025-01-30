package com.tsbgroup.practice.accountapp.Tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tsbgroup.practice.accountapp.Model.Consent;
import com.tsbgroup.practice.accountapp.Repository.ConsentRepository;
import com.tsbgroup.practice.accountapp.Service.ConsentService;

class ConsentServiceTest {

    // injecting ensures proper dependancy injection
    @InjectMocks
    private ConsentService consentService;

    //
    @Mock
    ConsentRepository consentRepository;


    private Consent consent;

    
    @BeforeEach
    void setup() {

        // initialise mocks eg annotated by @inject mocks or @mock
        MockitoAnnotations.openMocks(this);

        consent = new Consent();
        consent.setConsentId("urn-alphabank-intent-88379");
        consent.setUserId("1234");
        consent.setUserName("John Doe");
        consent.setCreationDateTime(LocalDateTime.now());
        consent.setExpirationDateTime(LocalDateTime.now().plusMonths(6));
        
        Consent.Account account = new Consent.Account();
        account.setAccountId("67890");
        account.setAccountType("Savings");
        consent.setAccount(account);
    }

    @Test
    void testSaveConsent() {
        // Mock the save operation
        when(consentRepository.save(any())).thenReturn(consent);
        
        Consent savedConsent = consentService.saveConsent(consent);

        assertNotNull(savedConsent);
        assertNotNull(savedConsent.getCreationDateTime());
        assertNotNull(savedConsent.getExpirationDateTime());
        verify(consentRepository, times(1)).save(consent);
    }

    @Test
    void testGetConsentById() {
        when(consentRepository.findByConsentId("urn-alphabank-intent-88379")).thenReturn(Optional.of(consent));

        Optional<Consent> foundConsent = consentService.getConsentById("urn-alphabank-intent-88379");

        assertTrue(foundConsent.isPresent());
        assertEquals("John Doe", foundConsent.get().getUserName());
    }

    @Test
    void testGetConsentById_NotFound() {
        when(consentRepository.findByConsentId("non-existent-id")).thenReturn(Optional.empty());

        Optional<Consent> foundConsent = consentService.getConsentById("non-existent-id");

        assertFalse(foundConsent.isPresent());
    }

    @Test
    void testUpdateConsent() {
        when(consentRepository.findByConsentId("urn-alphabank-intent-88379")).thenReturn(Optional.of(consent));
        when(consentRepository.save(any(Consent.class))).thenReturn(consent);

        Optional<Consent> updatedConsent = consentService.updateConsent("urn-alphabank-intent-88379", "Updated User");

        assertTrue(updatedConsent.isPresent());
        assertEquals("Updated User", updatedConsent.get().getUserName()); // ✅ Ensure name was updated
        verify(consentRepository, times(1)).save(any(Consent.class));
    }


    @Test
    void testUpdateConsent_NotFound() {
        when(consentRepository.findByConsentId("non-existent-id")).thenReturn(Optional.empty());

        Optional<Consent> updatedConsent = consentService.updateConsent("non-existent-id", "Updated User");

        assertFalse(updatedConsent.isPresent());
    }
}
