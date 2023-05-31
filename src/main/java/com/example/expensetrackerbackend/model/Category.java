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
    private String name;

}
