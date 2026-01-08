package com.attemp3.sc.mapper;

import com.attemp3.sc.dtos.register.RegisterRequest;
import com.attemp3.sc.dtos.register.RegisterResponse;
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

    // --- MAPPER CreateUserRequest TO ENTITY ---
    public static User toEntity(CreateUserRequest request) {
        return new User(request.getUsername(), request.getPassword());
    }

    // --- MAPPER RegisterRequest TO ENTITY ---
    public static User toEntity(RegisterRequest request) {
        return new User(request.getUsername(), request.getPassword());
    }

    // ---toListAllUsersResponse ---
    public static ListAllUsersResponse toListAllUsersResponse(User user) {
        return new ListAllUsersResponse(user.getId(), user.getUsername(), user.getRole().getId(), user.getRole().getName());
    }

    // -- toCreateUserResponse ---
    public static CreateUserResponse toCreateUserResponse(User user) {
        return new CreateUserResponse(user.getId(), user.getUsername(), user.getRole().getName());
    }

    // --- toReadOneUserResponse ---
    public static ReadOneUserResponse toReadOneUserResponse (User user){
        return new ReadOneUserResponse(user.getId(), user.getUsername(), user.getRole().getName());
    }

    // --- toRegisterResponse ----
    public static RegisterResponse toRegisterResponse (User user){
        return new RegisterResponse(user.getId(), user.getUsername(), user.getRole().getName());
    }

}
