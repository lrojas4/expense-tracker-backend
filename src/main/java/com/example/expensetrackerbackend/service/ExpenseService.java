package com.example.expensetrackerbackend.service;
import com.example.expensetrackerbackend.exception.InformationExistException;
import com.example.expensetrackerbackend.model.Expense;
import com.example.expensetrackerbackend.model.User;
import com.example.expensetrackerbackend.repository.ExpenseRepository;
import com.example.expensetrackerbackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    @Autowired
    public void setExpenseRepository(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Get the current logged-in user from jwt
     * @return logged-in user
     */
    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
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


    /**
     * Creates expense object
     * @param expenseObject expense object being added
     * @return the added expense object
     * @throws InformationExistException if expense already exists
     */
    public Optional<Expense> createExpense(Expense expenseObject) {
        expenseObject.setUser(ExpenseService.getCurrentLoggedInUser());
        return Optional.of(expenseRepository.save(expenseObject));
    }
}
