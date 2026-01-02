package com.attemp3.sc.dtos.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserResponse {
    private Long id;
    private String username;
    private String role;

}
