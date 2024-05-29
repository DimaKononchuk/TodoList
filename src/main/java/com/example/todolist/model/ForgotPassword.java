package com.example.todolist.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@Data
public class ForgotPassword {
    @NotEmpty(message = "Email cannot be empty!")
    private String email;
    @NotEmpty(message = "Code cannot be empty!")
    private String code;

    private String password;
    private String confirmPassword;

    public void clear(){
        email=null;
        code=null;
        password=null;
        confirmPassword=null;
    }
}
