package com.fdmgroup.practice.votingapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.practice.votingapp.Models.Poll;
import com.fdmgroup.practice.votingapp.repositories.PollRepository;

@Service
public class PollService {

    private final PollRepository pollRepository;
    
    @Autowired
    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }
    
    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

}
