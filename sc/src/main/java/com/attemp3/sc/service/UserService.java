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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    // READ ALL USERS
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

        if (!request.getPassword().trim().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedPassword);
        } else {
            throw new RuntimeException("empty password");
        }

        User savedUser = userRepository.save(user);

        return UserMapper.toCreateUserResponse(savedUser);
    }

    // DELETE
    public void deleteUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not founded"));

        userRepository.deleteById(id);
    }


    // UPDATE
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not founded"));

        userFound.setUsername(request.getUsername());

        Role role = roleRepository.findById(request.getIdRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        userFound.setRole(role);

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            userFound.setPassword(encodedPassword);
        }

        User updatedUser = userRepository.save(userFound);
        return UserMapper.toUserResponse(updatedUser);
    }

    // READ ONE USER
    public ReadOneUserResponse readUserById(Long id) {
        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return UserMapper.toReadOneUserResponse(userFound);
    }
}
