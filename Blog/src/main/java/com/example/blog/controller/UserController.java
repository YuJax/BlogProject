package com.example.blog.controller;


import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.Message;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder  passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody @Valid User user) {
        User tmp = userRepository.findByUsername(user.getUsername());
        Message message = new Message();
        if(tmp != null) {
            message.setMessage("Username already exists");
            return ResponseEntity.status(400).body(message);
        }

        String userPassword = user.getPassword();
        // user password use bcrypt
        user.setPassword(passwordEncoder.encode(userPassword));
        userRepository.save(user);
        message.setMessage("User created successfully");
        return ResponseEntity.status(201).body(message);
    }


    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody @Valid User user) {
        User tmp = userRepository.findByUsername(user.getUsername());
        Message message = new Message();
        if(tmp == null) {
            message.setMessage("Username does not exists");
            return ResponseEntity.status(400).body(message);
        }
        if(!passwordEncoder.matches(user.getPassword(), tmp.getPassword())) {
            message.setMessage("Password is incorrect");
            return ResponseEntity.status(400).body(message);
        }

        String key = "secret";
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", "user")
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, key)
                .compact();

        message.setMessage("Login successful");
        message.setToken(token);
        return ResponseEntity.status(200).body(message);
    }
}
