package com.example.todolist.payload;

import lombok.Data;

import javax.validation.constraints.*;


@Data
public class SignUpRequest {

    @Size(min = 5, max = 50, message = "Username must be from 5 to 50 chars.")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Size(min = 5, max = 255, message = "Email must be from 5 to 255 chars.")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email address must be like user@example.com")
    private String email;

    @Size(max = 255, message = "Password max lenght is 255")
    private String password;
}
