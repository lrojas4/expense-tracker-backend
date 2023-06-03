package com.example.expensetrackerbackend.service;
import com.example.expensetrackerbackend.model.Income;
import com.example.expensetrackerbackend.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    private IncomeRepository incomeRepository;

    @Autowired
    public void setIncomeRepository(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
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
}
