package com.cytek2.cytek.audit.controller.auth;

import com.cytek2.cytek.audit.dto.PasswordChangeRequest;
import com.cytek2.cytek.audit.model.Token;
import com.cytek2.cytek.audit.model.User;
import com.cytek2.cytek.audit.repository.TokenRepository;
import com.cytek2.cytek.audit.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class OTPValidate {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public OTPValidate(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/otp/validate")
    public ResponseEntity<String> validateOTP(@RequestParam("otp") String otp) {
        // Check if the provided OTP is valid (compare with the one stored in the database)
        if (isValidOTP(otp)) {
            return ResponseEntity.ok("OTP is valid");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

    // Utility method to validate the OTP
    private boolean isValidOTP(String otp) {
        System.out.println("started finding otp in db");
        // Fetch the stored token from the database based on the OTP
        Token storeOtp = tokenRepository.findByOtp(otp);
        System.out.println("found otp is :" + storeOtp);
        if (storeOtp != null) {
            // Check if the OTP has expired
            if (isValidExpirationDate(storeOtp.getExpiryDate())) {
                // Here, you can also compare the OTP from the database with the one provided
                // For simplicity, we assume they match for now
                System.out.println("it has not expired yet,it will expire on:" + storeOtp.getExpiryDate());
                return true;
            } else {
                System.out.println("OTP has expired,");
            }
        } else {
            System.out.println("OTP not found in the database");
        }

        return false;
    }


    // Utility method to check if the OTP has expired
    private boolean isValidExpirationDate(Date expiryDate) {
        Date currentDate = new Date();
        return !currentDate.after(expiryDate);
    }


    @PostMapping("/password/change")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        // Check if the provided OTP is valid (compare with the one stored in the database)
        String otp = passwordChangeRequest.getOtp();
        Token storedOtp = tokenRepository.findByOtp(otp);

        if (storedOtp != null && isValidExpirationDate(storedOtp.getExpiryDate())) {
            // OTP is valid, proceed with password change
            String password = passwordChangeRequest.getPassword();

            // Find the user associated with the OTP token using user_id
            Long userId = Long.valueOf(storedOtp.getUser().getId());
            User user = userRepository.findById(Math.toIntExact(userId)).orElse(null);

            if (user != null) {
                // Update the user's password
                user.setPassword(password);

                // Save the updated user entity
                userRepository.save(user);

                // Delete the OTP token
                tokenRepository.delete(storedOtp);

                return ResponseEntity.ok("Password changed successfully");
            } else {
                // User not found
                return ResponseEntity.badRequest().body("User not found");
            }
        } else {
            // Invalid OTP or expired OTP
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }
    }
}
