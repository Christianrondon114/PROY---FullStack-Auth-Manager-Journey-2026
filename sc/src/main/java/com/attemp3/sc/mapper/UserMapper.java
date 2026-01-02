package com.attemp3.sc.mapper;

import com.attemp3.sc.dtos.user.request.CreateUserRequest;
import com.attemp3.sc.dtos.user.response.CreateUserResponse;
import com.attemp3.sc.dtos.user.response.ListAllUsersResponse;
import com.attemp3.sc.dtos.user.response.ReadOneUserResponse;
import com.attemp3.sc.dtos.user.response.UserResponse;
import com.attemp3.sc.entities.User;

public class UserMapper {

    // -- MAP TO USER GENERAL RESPONSE --
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword());
    }

    // --- MAPPER TO ENTITY ---
    public static User toEntity(CreateUserRequest user) {
        return new User(user.getUsername(), user.getPassword());
    }

    // ---LIST USERS---
    public static ListAllUsersResponse toListAllUsersResponse(User user) {
        return new ListAllUsersResponse(user.getId(), user.getUsername(), user.getRole().getId(), user.getRole().getName());
    }

    // -- CREATE ---
    public static CreateUserResponse toCreateUserResponse(User user) {
        return new CreateUserResponse(user.getId(), user.getUsername(), user.getRole().getName());
    }

    // --- VIEW ---
    public static ReadOneUserResponse toReadOneUserResponse (User user){
        return new ReadOneUserResponse(user.getId(), user.getUsername(), user.getRole().getName());
    }

}
