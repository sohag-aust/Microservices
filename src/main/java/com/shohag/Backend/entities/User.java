package com.shohag.Backend.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false) // column name inside the db table
    private String name;
    private String email;
    private String password;
    private String about;

}
