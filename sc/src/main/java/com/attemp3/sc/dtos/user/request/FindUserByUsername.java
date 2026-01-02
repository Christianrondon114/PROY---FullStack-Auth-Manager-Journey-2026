package com.attemp3.sc.dtos.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FindUserByUsername {
    @NotBlank(message = "is required")
    private String username;
}
