package com.attemp3.sc.controllers.user;

import com.attemp3.sc.dtos.user.request.CreateUserRequest;
import com.attemp3.sc.dtos.user.request.UpdateUserRequest;
import com.attemp3.sc.dtos.user.response.CreateUserResponse;
import com.attemp3.sc.dtos.user.response.ListAllUsersResponse;
import com.attemp3.sc.dtos.user.response.ReadOneUserResponse;
import com.attemp3.sc.dtos.user.response.UserResponse;
import com.attemp3.sc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ----------------- CRUD USER -------------------------------------------

    @GetMapping
    public List<ListAllUsersResponse> showAllUsers() {
        return userService.showAllUsers();
    }

    @GetMapping("/{id}")
    public ReadOneUserResponse readUserById(@PathVariable Long id) {
        return userService.readUserById(id);
    }

    @PostMapping
    public CreateUserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

}


