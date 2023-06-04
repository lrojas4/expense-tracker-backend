package com.example.expensetrackerbackend.service;
import com.example.expensetrackerbackend.exception.InformationExistException;
import com.example.expensetrackerbackend.exception.InformationNotFoundException;
import com.example.expensetrackerbackend.model.Expense;
import com.example.expensetrackerbackend.model.Income;
import com.example.expensetrackerbackend.model.User;
import com.example.expensetrackerbackend.repository.IncomeRepository;
import com.example.expensetrackerbackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    private IncomeRepository incomeRepository;

    @Autowired
    public void setIncomeRepository(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
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
     * Gets a list of incomes
     * @return a list of incomes
     */
    public List<Income> getIncomes() {
        return incomeRepository.findAll();
    }

    /**
     * Gets income by income id
     * @param incomeId we are searching for
     * @return income based on id
     */
    public Income getIncome(Long incomeId) {
        return incomeRepository.findById(incomeId).orElse(null);
    }


    /**
     * Filters a list of incomes by user id
     * @param userId we are searching for
     * @return a list of incomes based on user id
     */
    public List<Income> getIncomesByUserId(Long userId) {
        return incomeRepository.findAll().stream().filter(income -> income.getUser().getId() == userId)
                .collect(Collectors.toList());
    }

    /**
     * Creates income object
     * @param incomeObject income object being added
     * @return the added income object
     * @throws InformationExistException if income already exists
     */
    public Optional<Income> createIncome(Income incomeObject) {
        incomeObject.setUser(IncomeService.getCurrentLoggedInUser());
        return Optional.of(incomeRepository.save(incomeObject));
    }

    /**
     * Updates income object
     * @param incomeId income id we are updating
     * @param incomeObject income object we are updating to
     * @return updated income
     * @throws InformationNotFoundException if property address not found
     */
    public Optional<Income> updateIncome(Long incomeId, Income incomeObject) {
        Optional<Income> income = incomeRepository.findByIdAndUserId(incomeId, IncomeService.getCurrentLoggedInUser().getId());
        if(income.isPresent()){
            income.get().setIncome_amount(incomeObject.getIncome_amount());
            return Optional.of(incomeRepository.save(income.get()));
        } else {
            throw new InformationNotFoundException("Income with id " + incomeObject.getId() + " doesn't exist");
        }
    }

    /**
     * Deletes income by income id
     * @param incomeId income id we are deleting
     * @return a String stating whether it was successfully deleted if expense id exists
     * @throws InformationNotFoundException if expense id does not exist
     */
    public String deleteIncome(Long incomeId) {
        Optional<Income> income = incomeRepository.findByIdAndUserId(incomeId, IncomeService.getCurrentLoggedInUser().getId());
        if(income.isPresent()) {
            incomeRepository.deleteById(incomeId);
            return "Income with id " + incomeId + " was deleted";
        } else {
            throw new InformationNotFoundException("Income with id: " + incomeId + " doesn't exist");
        }
    }
}
