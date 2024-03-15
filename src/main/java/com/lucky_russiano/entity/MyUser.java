package com.lucky_russiano.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MyUser {
    @Id
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private int salary;
}
