package com.example.backend.model;

import com.example.backend.utils.Shift;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String acronym;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    public Course(){}

    public Course(String name, String acronym, Shift shift){
        this.name = name;
        this.acronym = acronym;
        this.shift = shift;
    }

}
