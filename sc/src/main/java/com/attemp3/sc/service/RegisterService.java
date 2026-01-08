package com.attemp3.sc.service;

import com.attemp3.sc.dtos.register.RegisterRequest;
import com.attemp3.sc.dtos.register.RegisterResponse;
import com.attemp3.sc.entities.Role;
import com.attemp3.sc.entities.User;
import com.attemp3.sc.mapper.UserMapper;
import com.attemp3.sc.repository.RoleRepository;
import com.attemp3.sc.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest request)  {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username '" + request.getUsername() + "' already exists!");
        }

        if (!request.getPassword().equals(request.getPasswordRepeat())) {
            throw new RuntimeException("Passwords do not match!");
        }

        User user = UserMapper.toEntity(request);

        user.setUsername(request.getUsername());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        Role roleGuest = roleRepository.findById(4L)
                .orElseThrow(() -> new RuntimeException("Role doesn't exists"));

        user.setRole(roleGuest);

        try{
            User userRegistered = userRepository.save(user);
            return UserMapper.toRegisterResponse(userRegistered);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }
}
