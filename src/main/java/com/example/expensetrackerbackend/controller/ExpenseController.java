package com.example.expensetrackerbackend.controller;
import com.example.expensetrackerbackend.model.Expense;
import com.example.expensetrackerbackend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
     * @return List of properties
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

    /**
     * Calls on createExpense() from ExpenseService and returns status 201 if successful
     * @param expenseObject property we are adding
     * @return an expense object that we have added
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/expenses/")
    public Optional<Expense> createExpense(@RequestBody Expense expenseObject) {
        return expenseService.createExpense(expenseObject);
    }

    /**
     * Calls on updateExpense() from ExpenseService
     * @param expenseId expense id we are searching for
     * @param expenseObject expense object we are updating
     * @return an updated expense object
     */
    @PutMapping(path = "/expenses/{expenseId}/")
    public Optional<Expense> updateExpense(@PathVariable Long expenseId, @RequestBody Expense expenseObject) {
        return expenseService.updateExpense(expenseId,expenseObject);
    }


    /**
     * Calls on getExpensesByUserId() through expenseService
     * @return List of expenses
     */
    @GetMapping(path = "/expenses/user/{userId}")
    public List<Expense> getExpensesByUserId(@PathVariable Long userId) {
        return expenseService.getExpenseByUserId(userId);
    }

    /**
     * Calls on deleteExpense() from ExpenseService
     * @param expenseId expense id we are searching for
     * @return a String stating whether the deletion was successful, throws an exception otherwise.
     */
    @DeleteMapping(path = "/expenses/{expenseId}")
    public String deleteExpense(@PathVariable Long expenseId) {
        return expenseService.deleteExpense(expenseId);
    }
}
