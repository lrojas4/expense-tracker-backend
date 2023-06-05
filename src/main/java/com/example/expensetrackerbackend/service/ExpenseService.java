package com.example.expensetrackerbackend.service;
import com.example.expensetrackerbackend.exception.InformationExistException;
import com.example.expensetrackerbackend.exception.InformationNotFoundException;
import com.example.expensetrackerbackend.model.Expense;
import com.example.expensetrackerbackend.model.User;
import com.example.expensetrackerbackend.repository.ExpenseRepository;
import com.example.expensetrackerbackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Get the current logged-in user from jwt
     * @return logged-in user
     */
    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    /**
     * Filters a list of expenses by user id
     * @param userId we are searching for
     * @return a list of expenses based on user id
     */
    public List<Expense> getExpenseByUserId(Long userId){
        return expenseRepository.findAll().stream().filter(expense -> expense.getUser().getId() == userId)
                .collect(Collectors.toList());
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

    /**
     * Updates expense object
     * @param expenseId expense id we are updating
     * @param expenseObject expense object we are updating to
     * @return updated expense
     * @throws InformationNotFoundException if property address not found
     */
    public Optional<Expense> updateExpense(Long expenseId, Expense expenseObject) {
        Optional<Expense> expense = expenseRepository.findByIdAndUserId(expenseId, ExpenseService.getCurrentLoggedInUser().getId());
        if(expense.isPresent()){
            expense.get().setDate(expenseObject.getDate());
            expense.get().setAmount(expenseObject.getAmount());
            expense.get().setDescription(expenseObject.getDescription());
            return Optional.of(expenseRepository.save(expense.get()));
        } else {
            throw new InformationNotFoundException("Expense with id " + expenseObject.getId() + " doesn't exist");
        }
    }

    /**
     * Deletes expense by expense id
     * @param expenseId expense id we are deleting
     * @return a String stating whether it was successfully deleted if expense id exists
     * @throws InformationNotFoundException if expense id does not exist
     */
    public String deleteExpense(Long expenseId) {
        Optional<Expense> expense = expenseRepository.findByIdAndUserId(expenseId, ExpenseService.getCurrentLoggedInUser().getId());
        if(expense.isPresent()) {
            expenseRepository.deleteById(expenseId);
            return "Expense with id " + expenseId + " was deleted";
        } else {
            throw new InformationNotFoundException("Expense with id: " + expenseId + " doesn't exist");
        }
    }

    /**
     * Gets Expenses by Category id
     * @param categoryId category id we are searching for
     * @return a list of expenses by category
     */

    public List<Expense> getExpensesByCategoryId(Long categoryId) {
        Optional<List<Expense>> expenses = expenseRepository.findAllByUserIdAndCategoryId(getCurrentLoggedInUser().getId(), categoryId);
        if(expenses.isPresent()) {
            return expenses.get();
        } else {
            throw new InformationNotFoundException("Expenses for user: " + getCurrentLoggedInUser().getId() + " in category " + categoryId + " doesn't exist");
        }
    }
}
