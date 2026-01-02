package com.attemp3.sc.controllers;


import com.attemp3.sc.dtos.auth.LoginRequest;
import com.attemp3.sc.dtos.auth.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/logg")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
            return ResponseEntity.ok(new LoginResponse("ok", request.getUsername()));
        }
}




