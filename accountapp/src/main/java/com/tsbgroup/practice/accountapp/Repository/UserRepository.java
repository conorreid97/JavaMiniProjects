package com.tsbgroup.practice.accountapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsbgroup.practice.accountapp.Model.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);
}
