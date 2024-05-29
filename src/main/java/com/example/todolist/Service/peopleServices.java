package com.example.todolist.Service;

import com.example.todolist.Repository.peopleRepository;
import com.example.todolist.model.ForgotPassword;
import com.example.todolist.model.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class peopleServices {
    @Autowired
    private peopleRepository peopleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean changePassword(ForgotPassword forgotPassword) {
        if (forgotPassword.getPassword().equals(forgotPassword.getConfirmPassword())){
            peopleRepository.changePassword(forgotPassword.getEmail(), passwordEncoder.encode(forgotPassword.getPassword()));
            return true;
        }else{
            return false;
        }

    }


    public Optional<People> findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    public People save(People people) {
        return peopleRepository.save(people);
    }
}
