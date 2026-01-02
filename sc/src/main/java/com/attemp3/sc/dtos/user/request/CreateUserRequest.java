package com.attemp3.sc.dtos.user.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserRequest {
    @NotBlank(message = "is required")
    private String username;
    @NotBlank(message = "is required")
    private String password;
    @NotNull(message = "is required")
    private Long idRole;


}