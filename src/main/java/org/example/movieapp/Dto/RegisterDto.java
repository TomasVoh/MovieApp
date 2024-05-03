package org.example.movieapp.Dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String surname;
    private String nickname;
    private String email;
    private String password;
}
