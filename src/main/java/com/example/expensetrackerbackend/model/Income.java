package com.example.expensetrackerbackend.model;
import javax.persistence.*;

@Entity
@Table(name= "incomes")
public class Income {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double income_amount;

    public Income() {
    }

    public Income(Long id, double income_amount) {
        this.id = id;
        this.income_amount = income_amount;
    }
}
