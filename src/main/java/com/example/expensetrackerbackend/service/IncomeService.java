package com.example.expensetrackerbackend.service;

import com.example.expensetrackerbackend.repository.ExpenseRepository;
import com.example.expensetrackerbackend.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

    private IncomeRepository incomeRepository;

    @Autowired
    public void setIncomeRepository(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }
}
