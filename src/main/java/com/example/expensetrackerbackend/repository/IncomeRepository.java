package com.example.expensetrackerbackend.repository;

import com.example.expensetrackerbackend.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    Optional<Income> findById(Long id);

    Optional<Income> findByIdAndUserId(Long id, Long userId);

    Optional<List<Income>> findAllByUserId(Long userId);
}
