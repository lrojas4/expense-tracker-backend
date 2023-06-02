package com.example.expensetrackerbackend.repository;
import com.example.expensetrackerbackend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findById(Long id);

    Optional<Expense> findByIdAndUserId(Long id, Long userId);

    Optional<List<Expense>> findAllByUserId(Long userId);
}

