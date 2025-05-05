package com.example.backend.DTO;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
