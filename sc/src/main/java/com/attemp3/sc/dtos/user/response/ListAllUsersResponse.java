package com.attemp3.sc.dtos.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListAllUsersResponse {
    private Long id;
    private String username;
    private Long idRole;
    private String roleName;
}
