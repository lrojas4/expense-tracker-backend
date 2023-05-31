package com.example.expensetrackerbackend.model;
import javax.persistence.*;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category_name;

    public Category() {
    }

    public Category(Long id, String category_name) {
        this.id = id;
        this.category_name = category_name;
    }
}
