package com.example.expensetrackerbackend.model;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name= "expenses")
public class Expense {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date date;

    @Column
    private double amount;

    @Column
    private String description;

    public Expense() {
    }

    public Expense(Long id, Date date, double amount, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }
}
