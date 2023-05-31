package com.example.Project.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByUsername(String username);
    public User findUserById(Long id);
    public List<User> findAllByOnline(boolean online);
}
