package com.trading.trading.controller;

import com.trading.trading.domain.VerificationType;
import com.trading.trading.model.User;
import com.trading.trading.model.VerificationCode;
import com.trading.trading.service.EmailService;
import com.trading.trading.service.UserService;
import com.trading.trading.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
  @Autowired
    private UserService userService;
  private VerificationCodeService verificationCodeService;
  @Autowired
  private EmailService emailService;
@GetMapping("/api/users/profile")
  public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) {
      User user = userService.findUserProfileByJwt(jwt);

      return new ResponseEntity<User>(user, HttpStatus.OK);
  }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception {
  User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        if (verificationCode == null) {
verificationCode= verificationCodeService.sendVerificationCode(user, verificationType);
        }

        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationEmail(user.getEmail(),verificationCode.getOtp());
        }
  return new ResponseEntity<>("verification otp sent successfully", HttpStatus.OK);
    }


    @PatchMapping("/api/users/profile/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
           @PathVariable String otp,
            @RequestHeader("Authorization") String jwt)
            throws Exception {


        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        String sendTo= verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? user.getEmail() : verificationCode.getOtp();
        boolean isVerified = verificationCode.getOtp().equals(otp);
        if(isVerified) {
            User updateUser=userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        }
     throw new Exception("Wrong otp");
    }
}
