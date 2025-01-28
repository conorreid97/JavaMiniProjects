package com.fdmgroup.practice.votingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.practice.votingapp.Models.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long>{

}
