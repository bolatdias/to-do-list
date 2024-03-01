package com.example.todolist.payload;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class LoginRequest {

    @Size(min = 5, max = 50, message = "Username size must be 8 and 255")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Size(min = 8, max = 255, message = "Password size must be 8 and 255")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
