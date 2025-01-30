package com.tsbgroup.practice.accountapp.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tsbgroup.practice.accountapp.Model.Consent;
import com.tsbgroup.practice.accountapp.Service.ConsentService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ConsentController {

    @Autowired
    private ConsentService consentService;

    @PostMapping("/consent")
    public Map<String, Object> createConsent(@RequestBody Consent consentRequest) {
        // Save the consent
        Consent savedConsent = consentService.saveConsent(consentRequest);

        // Build the response
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        data.put("ConsentId", savedConsent.getConsentId());
        data.put("Status", "AwaitingAuthorisation");
        data.put("StatusUpdateDateTime", LocalDateTime.now());
        data.put("CreationDateTime", savedConsent.getCreationDateTime());
        data.put("Permissions", Arrays.asList(
                "ReadAccountsDetail", "ReadBalances", "ReadBeneficiariesDetail", "ReadDirectDebits",
                "ReadProducts", "ReadStandingOrdersDetail", "ReadTransactionsCredits", "ReadTransactionsDebits",
                "ReadTransactionsDetail", "ReadOffers", "ReadPAN", "ReadParty", "ReadPartyPSU",
                "ReadScheduledPaymentsDetail", "ReadStatementsDetail"
        ));
        data.put("ExpirationDateTime", savedConsent.getExpirationDateTime());
        data.put("TransactionFromDateTime", LocalDateTime.now().plusDays(1));
        data.put("TransactionToDateTime", LocalDateTime.now().plusMonths(6));

        Map<String, Object> links = new HashMap<>();
        links.put("Self", "https://api.alphabank.com/open-banking/v3.1/aisp/account-access-consents/" + savedConsent.getConsentId());

        Map<String, Object> meta = new HashMap<>();
        meta.put("TotalPages", 1);

        response.put("Data", data);
        response.put("Risk", new HashMap<>());
        response.put("Links", links);
        response.put("Meta", meta);

        return response;
    }

    @GetMapping("/consent/{consentId}")
    public Optional<Consent> getConsentById(@PathVariable String consentId) {
        return consentService.getConsentById(consentId);
    }

    @PatchMapping("/consent/{consentId}")
    public Optional<Consent> updateConsent(@PathVariable String consentId, @RequestBody Map<String, String> updates) {
        if (updates.containsKey("userName")) {
            return consentService.updateConsent(consentId, updates.get("userName"));
        }
        return Optional.empty();
    }
    
}
