package com.tsbgroup.practice.accountapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsbgroup.practice.accountapp.Model.Consent;

public interface ConsentRepository extends JpaRepository<Consent, Long> {

}
