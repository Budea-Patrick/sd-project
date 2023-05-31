package com.example.Project.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean online;

    public User(String username, String password) {
        this.username = username;
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
    }

    public User(Long id) {
        this.id = id;
    }

    public User() {

    }
}
