package com.trading.trading.service;

import com.trading.trading.domain.VerificationType;
import com.trading.trading.model.User;

public interface UserService {
    public User findUserProfileByJwt(final String jwt);
    public User findUserByEmail(final String email);
    public User findUserById(final Long id);

    public User enableTwoFactorAuthentication(VerificationType verificationType, String SendTo, User user);
    User updatePassword(User user, String newPassword);

}
