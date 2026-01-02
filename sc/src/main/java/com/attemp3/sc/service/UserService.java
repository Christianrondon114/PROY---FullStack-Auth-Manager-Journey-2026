package com.attemp3.sc.service;

import com.attemp3.sc.dtos.user.request.CreateUserRequest;
import com.attemp3.sc.dtos.user.request.UpdateUserRequest;
import com.attemp3.sc.dtos.user.response.CreateUserResponse;
import com.attemp3.sc.dtos.user.response.ListAllUsersResponse;
import com.attemp3.sc.dtos.user.response.ReadOneUserResponse;
import com.attemp3.sc.dtos.user.response.UserResponse;
import com.attemp3.sc.entities.Role;
import com.attemp3.sc.entities.User;
import com.attemp3.sc.mapper.UserMapper;
import com.attemp3.sc.repository.RoleRepository;
import com.attemp3.sc.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // READ
    public List<ListAllUsersResponse> showAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserMapper::toListAllUsersResponse)
                .toList();
    }

    // CREATE USER
    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = UserMapper.toEntity(request);

        user.setPassword(user.getPassword());

        Role role = roleRepository.findById(request.getIdRole())
                .orElseThrow(() -> new RuntimeException("Error: El rol con ID " + request.getIdRole() + " no existe."));

        user.setRole(role);

        User savedUser = userRepository.save(user);

        return UserMapper.toCreateUserResponse(savedUser);
    }

    // DELETE
    public void deleteUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not founded"));

        userRepository.deleteById(id);
    }


    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not founded"));

        userFound.setUsername(request.getUsername());

        Role role = roleRepository.findById(request.getIdRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        userFound.setRole(role);

        if (!request.getPassword().trim().isEmpty()) {
            userFound.setPassword(request.getPassword());
        }

        User updatedUser = userRepository.save(userFound);
        return UserMapper.toUserResponse(updatedUser);
    }

    public ReadOneUserResponse readUserById(Long id) {
        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return UserMapper.toReadOneUserResponse(userFound);
    }
}
