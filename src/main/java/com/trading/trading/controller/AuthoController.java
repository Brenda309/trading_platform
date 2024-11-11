package com.trading.trading.controller;

import com.trading.trading.Config.JwtProvider;
import com.trading.trading.model.User;
import com.trading.trading.repository.UserRepository;
import com.trading.trading.response.AuthRespone;
import com.trading.trading.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

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

    @PostMapping("/signin")
    public ResponseEntity<AuthRespone> login(@RequestBody User user) {

String userName = user.getEmail();
String password = user.getPassword();
        Authentication auth = authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);

        AuthRespone res = new AuthRespone();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login successfully");


        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    private Authentication authenticate(String userName, String password) {
UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
if(userDetails == null) {
    throw new BadCredentialsException ("Invalid username or password");
}
if(!password.equals(userDetails.getPassword())) {
    throw new BadCredentialsException ("Invalid password");
}
return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
}
}
