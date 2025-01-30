package com.tsbgroup.practice.accountapp.Tests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.Optional;

import com.tsbgroup.practice.accountapp.Controller.ConsentController;
import com.tsbgroup.practice.accountapp.Model.Consent;
import com.tsbgroup.practice.accountapp.Service.ConsentService;

public class ConsentControllerTest {

    @InjectMocks
    private ConsentController consentController;

    @Mock
    private ConsentService consentService;

    private MockMvc mockMvc;

    private Consent consent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(consentController).build();

        consent = new Consent();
        consent.setConsentId("urn-alphabank-intent-88379");
        consent.setUserId("12345");
        consent.setUserName("John Doe");
        consent.setCreationDateTime(LocalDateTime.now());
    consent.setExpirationDateTime(LocalDateTime.now().plusMonths(6));
        Consent.Account account = new Consent.Account();
        account.setAccountId("67890");
        account.setAccountType("Savings");
        consent.setAccount(account);
    }

    @Test
    void testCreateConsent() throws Exception {

        // Mock the service response
        when(consentService.saveConsent(any(Consent.class))).thenReturn(consent);
    
        String requestBody = """
                {
                    "consentId": "urn-alphabank-intent-88379",
                    "userId": "12345",
                    "userName": "John Doe",
                    "account": {
                        "accountId": "67890",
                        "accountType": "Savings"
                    }
                }
                """;
    
        mockMvc.perform(post("/api/consent")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isOk());
    }

    @Test
    void testGetConsentById() throws Exception {
        when(consentService.getConsentById("urn-alphabank-intent-88379"))
                .thenReturn(Optional.of(consent));

        mockMvc.perform(get("/api/consent/urn-alphabank-intent-88379")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consentId").value("urn-alphabank-intent-88379"));
    }

    @Test
    void testUpdateConsent() throws Exception {
        consent.setUserName("UpdatedUser"); 

        when(consentService.updateConsent("urn-alphabank-intent-88379", "UpdatedUser"))
                .thenReturn(Optional.of(consent)); 

        mockMvc.perform(patch("/api/consent/urn-alphabank-intent-88379")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "userName": "UpdatedUser"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("UpdatedUser")); 
    }
}
