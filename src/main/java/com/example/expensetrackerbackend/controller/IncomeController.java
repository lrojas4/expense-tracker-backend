package com.example.expensetrackerbackend.controller;
import com.example.expensetrackerbackend.model.Expense;
import com.example.expensetrackerbackend.model.Income;
import com.example.expensetrackerbackend.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/")
public class IncomeController {

    private IncomeService incomeService;

    @Autowired
    public void setIncomeService(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    /**
     * Calls on getIncomes() through expenseService
     * @return List of properties
     */
    @GetMapping(path = "/incomes/")
    public List<Income> getIncomes() {
        return incomeService.getIncomes();
    }

    /**
     * Calls on getIncome() from IncomeService
     * @param incomeId income id we are searching for
     * @return an income based on income id
     */
    @GetMapping(path = "/incomes/{incomeId}/")
    public Income getExpense(@PathVariable Long incomeId) {
        return incomeService.getIncome(incomeId);
    }

    /**
     * Calls on createIncome() from IncomeService and returns status 201 if successful
     * @param incomeObject property we are adding
     * @return an income object that we have added
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/incomes/")
    public Optional<Income> createIncome(@RequestBody Income incomeObject) {
        return incomeService.createIncome(incomeObject);
    }

    /**
     * Calls on getIncomesByUserId() through incomeService
     * @return List of incomes
     */
    @GetMapping(path = "/incomes/user/{userId}")
    public List<Income> getIncomesByUserId(@PathVariable Long userId) {
        return incomeService.getIncomesByUserId(userId);
    }

    /**
     * Calls on updateIncome() from IncomeService
     * @param incomeId income id we are searching for
     * @param incomeObject income object we are updating
     * @return an updated income object
     */
    @PutMapping(path = "/incomes/{incomeId}/")
    public Optional<Income> updateIncome(@PathVariable Long incomeId, @RequestBody Income incomeObject) {
        return incomeService.updateIncome(incomeId,incomeObject);
    }
}
