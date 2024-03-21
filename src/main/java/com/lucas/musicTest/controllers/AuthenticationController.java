package com.lucas.musicTest.controllers;

import com.lucas.musicTest.dtos.AuthenticationRecordDTO;
import com.lucas.musicTest.dtos.LoginResponseRecordDTO;
import com.lucas.musicTest.dtos.RegisterRecordDTO;
import com.lucas.musicTest.infra.security.TokenService;
import com.lucas.musicTest.models.user.UserModel;
import com.lucas.musicTest.models.user.UserRole;
import com.lucas.musicTest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager AuthenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRecordDTO data) {
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authenticated = this.AuthenticationManager.authenticate(usernamePasswordToken);

        var token = tokenService.generateToken((UserModel) authenticated.getPrincipal());

        return ResponseEntity.ok(new LoginResponseRecordDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody @Valid RegisterRecordDTO data) {
        if (userRepository.findByLogin(data.login()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel user = new UserModel(data.login(), encryptedPassword, data.role());

        var userCreated = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
}
