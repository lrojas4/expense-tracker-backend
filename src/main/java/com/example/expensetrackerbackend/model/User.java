package com.example.expensetrackerbackend.model;
import javax.persistence.*;


@Entity
@Table(name= "users")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;


}
