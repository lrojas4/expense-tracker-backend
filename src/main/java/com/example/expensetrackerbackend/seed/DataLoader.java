package com.example.expensetrackerbackend.seed;
import com.example.expensetrackerbackend.model.Expense;
import com.example.expensetrackerbackend.model.User;
import com.example.expensetrackerbackend.repository.ExpenseRepository;
import com.example.expensetrackerbackend.repository.UserRepository;
import com.example.expensetrackerbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

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
        }
    }
}
