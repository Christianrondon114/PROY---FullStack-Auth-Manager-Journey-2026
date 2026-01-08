package com.attemp3.sc.dtos.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @Size(min = 2, message = "Password must be at least 8 characters")
    private String password;
    private String passwordRepeat;

}
