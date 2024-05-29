package com.example.todolist.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
@Table(name = "notes")
@Getter
@Setter
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="notes")
    @NotEmpty(message = "Notes canot be empty!")
    private String notes;

    @Column(name="description")
    @NotEmpty(message = "Description canot be empty!")
    private String description;

    @Column(name = "date")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "people_id")
    private People people;

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }
}
