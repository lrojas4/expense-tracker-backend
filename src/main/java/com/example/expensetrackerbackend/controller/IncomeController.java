package com.example.expensetrackerbackend.controller;
import com.example.expensetrackerbackend.model.Income;
import com.example.expensetrackerbackend.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * Calls on getIncomesByUserId() through incomeService
     * @return List of incomes
     */
    @GetMapping(path = "/incomes/user/{userId}")
    public List<Income> getIncomesByUserId(@PathVariable Long userId) {
        return incomeService.getIncomesByUserId(userId);
    }
}
