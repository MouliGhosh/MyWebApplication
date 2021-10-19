package com.myproject.myproject.datainterface;

import com.myproject.myproject.data.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterface extends JpaRepository<User, Integer> {
    User findByName(String name);
    User findByUsername(String username);
    User findByEmail(String email); 
    User findByIdAndEmail(int id, String email);
}
