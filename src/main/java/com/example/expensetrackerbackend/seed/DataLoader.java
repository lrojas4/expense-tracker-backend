package com.example.expensetrackerbackend.seed;
import com.example.expensetrackerbackend.model.Expense;
import com.example.expensetrackerbackend.model.Income;
import com.example.expensetrackerbackend.model.User;
import com.example.expensetrackerbackend.repository.ExpenseRepository;
import com.example.expensetrackerbackend.repository.IncomeRepository;
import com.example.expensetrackerbackend.repository.UserRepository;
import com.example.expensetrackerbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;


@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if (expenseRepository.count() == 0 || userRepository.count() == 0) {
            User user1 = new User(1L, "Bob", "mail1@gmail.com", "psw1");
            User user2 = new User(2L, "Tom", "mail2@gmail.com", "psw2");
            User user3 = new User(3L, "Ann", "mail3@gmail.com", "psw3");
            userService.registerUser(user1);
            userService.registerUser(user2);
            userService.registerUser(user3);

            Expense expense1 = new Expense(1L, LocalDate.of(2023, 05, 01), 1600.00, "rent");
            Expense expense2 = new Expense(2L, LocalDate.of(2023, 05, 5), 100.00, "groceries");
            Expense expense3 = new Expense(3L, LocalDate.of(2023, 05, 10), 30.00, "transportation");
            Expense expense4 = new Expense(4L, LocalDate.of(2023, 05, 23), 50.00, "entertainment");
            Expense expense5 = new Expense(5L, LocalDate.of(2023, 05, 30), 200.00, "insurance");

            expense1.setUser(user1);
            expense2.setUser(user1);
            expense3.setUser(user2);
            expense4.setUser(user3);
            expense5.setUser(user3);

            expenseRepository.save(expense1);
            expenseRepository.save(expense2);
            expenseRepository.save(expense3);
            expenseRepository.save(expense4);
            expenseRepository.save(expense5);

            Income income1 = new Income(1L, 2000.00);
            Income income2 = new Income(2L, 3000.00);
            Income income3 = new Income(3L, 7000.00);
            Income income4 = new Income(4L, 5000.00);
            Income income5 = new Income(5L, 1000.00);

            income1.setUser(user1);
            income2.setUser(user1);
            income3.setUser(user2);
            income4.setUser(user3);
            income5.setUser(user3);

            incomeRepository.save(income1);
            incomeRepository.save(income2);
            incomeRepository.save(income3);
            incomeRepository.save(income4);
            incomeRepository.save(income5);


        }
    }
}
