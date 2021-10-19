package com.myproject.myproject.security;

import com.myproject.myproject.datainterface.UserInterface;

import com.myproject.myproject.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryStudentDetailsService implements UserDetailsService {

    
    @Autowired
    private UserInterface userInterface;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userInterface.findByUsername(username);
        
    if (user != null) {
        return user;
    }

    throw new UsernameNotFoundException("User: " + username + " not found!");
}
}