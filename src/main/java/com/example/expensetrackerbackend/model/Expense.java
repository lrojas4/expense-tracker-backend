package com.example.expensetrackerbackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name= "expenses")
public class Expense {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private double amount;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnore
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Expense() {
    }

    public Expense(Long id, LocalDate date, double amount, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
