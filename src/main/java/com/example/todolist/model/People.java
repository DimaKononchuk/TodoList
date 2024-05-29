package com.example.todolist.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "people")
@Getter
@Setter
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    //@NotEmpty(message = "User's name cannot be empty.")
    @Size(min = 2, max = 10 ,message = "User's name must be between 2 and 10 characters long")

    private String name;
    @NotNull
    @Size(min = 2, max = 10 ,message = "User's surname must be between 2 and 10 characters long")
    @NotEmpty(message = "User's surname cannot be empty.")
    private String surname;
    @NotBlank(message = "Email is mandatory")
    //@Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "User's login cannot be empty.")
    private String login;
    @NotEmpty(message = "User's password cannot be empty.")
    private String password;
    private String role;
    @OneToMany(mappedBy = "people",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Notes> notesList;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String img;

}
