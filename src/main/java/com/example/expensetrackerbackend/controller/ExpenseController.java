package com.example.expensetrackerbackend.controller;


import com.example.expensetrackerbackend.model.Expense;
import com.example.expensetrackerbackend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/")
public class ExpenseController {
    private ExpenseService expenseService;

    @Autowired
    public void setExpenseService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Calls on getExpenses() through expenseService
     * @return List of expenses
     */
    @GetMapping(path = "/expenses/")
    public List<Expense> getExpenses() {
        return expenseService.getExpenses();
    }

    /**
     * Calls on getExpense() from ExpenseService
     * @param expenseId expense id we are searching for
     * @return an expense based on expense id
     */
    @GetMapping(path = "/expenses/{expenseId}/")
    public Expense getExpense(@PathVariable Long expenseId) {
        return expenseService.getExpense(expenseId);
    }
}
