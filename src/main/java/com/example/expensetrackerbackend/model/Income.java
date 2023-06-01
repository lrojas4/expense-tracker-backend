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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Income() {
    }

    public Income(Long id, double income_amount) {
        this.id = id;
        this.income_amount = income_amount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getIncome_amount() {
        return income_amount;
    }

    public void setIncome_amount(double income_amount) {
        this.income_amount = income_amount;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", income_amount=" + income_amount +
                '}';
    }
}
