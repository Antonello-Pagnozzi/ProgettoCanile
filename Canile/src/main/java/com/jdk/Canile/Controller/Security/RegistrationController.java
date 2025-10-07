package com.jdk.Canile.Controller.Security;

import com.jdk.Canile.DTO.Security.RegistrationRequest;
import com.jdk.Canile.Service.Security.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest request) {
        registrationService.registerUser(request.getUsername(), request.getPassword(), request.getEmail());
        return "Registrazione completata con successo!";
    }
}
