package com.fdmgroup.practice.votingapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fdmgroup.practice.votingapp.Models.OptionVote;
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

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }

    public void vote(Long pollId, int optionIndex) {
        // Get Poll from DB
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found"));
        // Get all Options
        List<OptionVote> options = poll.getOptions();

        // If index for vote is not vali, throw error
        if(optionIndex < 0 || optionIndex >= options.size()){
            throw new IllegalArgumentException("Invalid option index");
        }
        
        
        // Get Selected Option
        OptionVote selectedOption = options.get(optionIndex);

        // Increment vote for selected option
        selectedOption.setVoteCount(selectedOption.getVoteCount() + 1);

        //Save incremented option into the database
        pollRepository.save(poll);
    }

}
