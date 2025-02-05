package com.tsbgroup.practice.accountapp.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsbgroup.practice.accountapp.Model.Consent;
import com.tsbgroup.practice.accountapp.Model.User;
import com.tsbgroup.practice.accountapp.Repository.ConsentRepository;
import com.tsbgroup.practice.accountapp.Repository.UserRepository;

import feign.FeignException;
import reactor.core.publisher.Mono;

@Service
public class ConsentService {

    @Autowired
    private ConsentRepository consentRepository;
    private final UserRepository userRepository;
    private final UserServiceClient userServiceClient;
    private final UserServiceFeignClient userServiceFeignClient;
    
    public ConsentService(ConsentRepository consentRepository
                            , UserRepository userRepository
                            , UserServiceClient userServiceClient
                            , UserServiceFeignClient userServiceFeignClient) {
        this.consentRepository = consentRepository;
        this.userRepository = userRepository;
        this.userServiceClient = userServiceClient;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    public Consent saveConsent(Consent consent) {
        consent.setCreationDateTime(LocalDateTime.now());
        consent.setExpirationDateTime(LocalDateTime.now().plusMonths(6));

        // Automatically save user if they don't exist
        Optional<User> existingUser = userRepository.findByUserId(consent.getUserId());
        if (existingUser.isEmpty()){
            User newUser = new User(consent.getUserId(), consent.getUserName());
            userRepository.save(newUser);
            System.out.println("Auto-created user: " + newUser.getUserId());
        }
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

    public Mono<String> fetchUserDetails(String userId){
        return userServiceClient.getUserDetails(userId);
    }

    public String fetchUserDetailsUsingFeign(String userId){
        try {
            System.out.println("Calling FeignClient for User ID: " + userId);
            return userServiceFeignClient.getUserDetails(userId);
        } catch (FeignException e) {
            System.out.println("Feign Client Error: " + e.getMessage());
            return "User not found (FeignClient)";
        }
    }
}
