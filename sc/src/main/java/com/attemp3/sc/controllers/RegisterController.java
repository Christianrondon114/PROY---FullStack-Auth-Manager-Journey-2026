package com.attemp3.sc.controllers;

import com.attemp3.sc.dtos.register.RegisterRequest;
import com.attemp3.sc.dtos.register.RegisterResponse;
import com.attemp3.sc.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    // ----------- REGISTER -------------
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid RegisterRequest userRegistered){
        return registerService.register(userRegistered);


    }
}
