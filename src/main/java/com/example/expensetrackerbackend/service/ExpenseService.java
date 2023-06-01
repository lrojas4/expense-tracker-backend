package com.example.expensetrackerbackend.service;

import com.example.expensetrackerbackend.model.Expense;
import com.example.expensetrackerbackend.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    @Autowired
    public void setExpenseRepository(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Gets a list of expenses
     * @return a list of expenses
     */
    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    /**
     * Gets expense by expense id
     * @param expenseId we are searching for
     * @return expense based on id
     */
    public Expense getExpense(Long expenseId) {
        return expenseRepository.findById(expenseId).orElse(null);
    }

}
