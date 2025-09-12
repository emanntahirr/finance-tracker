package com.emantahir.finance_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emantahir.finance_tracker.model.Investment;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
}
