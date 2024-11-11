package com.trading.trading.controller;

import com.trading.trading.Config.JwtProvider;
import com.trading.trading.model.User;
import com.trading.trading.repository.UserRepository;
import com.trading.trading.response.AuthRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthoController {

    @Autowired
    private UserRepository userRepository;

@PostMapping("/signup")
    public ResponseEntity<AuthRespone> register(@RequestBody User user) {

User isEmailExist = userRepository.findByEmail(user.getEmail());
if (isEmailExist != null) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
}
    User newUser= new User();
    newUser.setFullName(user.getFullName());
    newUser.setPassword(user.getPassword());
    newUser.setEmail(user.getEmail());
    User savedUser = userRepository.save(newUser);

    Authentication auth = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
          user.getPassword()
    );
    SecurityContextHolder.getContext().setAuthentication(auth);
String jwt = JwtProvider.generateToken(auth);

AuthRespone res = new AuthRespone();
res.setJwt(jwt);
res.setStatus(true);
res.setMessage("User registered successfully");


    return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
}
