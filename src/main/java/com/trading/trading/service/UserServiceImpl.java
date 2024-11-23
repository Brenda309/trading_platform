package com.trading.trading.service;

import com.trading.trading.Config.JwtProvider;
import com.trading.trading.domain.VerificationType;
import com.trading.trading.model.TwoFactorAuth;
import com.trading.trading.model.User;
import com.trading.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserServiceImpl implements UserService {
@Autowired
    private  UserRepository userRepository;
    @Override
    public User findUserProfileByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User findUserById(Long id) {
       Optional<User> user = userRepository.findById(id);
       if(user.isEmpty()){
           throw new UsernameNotFoundException("User not found");
       }

        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String SendTo, User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.isEnabled = true;
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);


        return userRepository.save(user);
    }


    @Override
    public User updatePassword(User user, String newPassword) {
       user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
