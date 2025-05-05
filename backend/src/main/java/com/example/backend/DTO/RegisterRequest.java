package com.example.backend.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String username;
    private String password;
}
