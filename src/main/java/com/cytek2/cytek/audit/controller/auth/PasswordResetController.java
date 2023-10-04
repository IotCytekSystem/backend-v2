package com.cytek2.cytek.audit.controller.auth;

import com.cytek2.cytek.audit.model.Token;
import com.cytek2.cytek.audit.model.User;
import com.cytek2.cytek.audit.repository.TokenRepository;
import com.cytek2.cytek.audit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PasswordResetController {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository; // Inject the TokenRepository
    private final JavaMailSender javaMailSender;

    @Autowired
    public PasswordResetController(UserRepository userRepository, TokenRepository tokenRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/api/reset-password/request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam("email") String email) {
        // Check if the provided email exists in the database
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found");
        }
        System.out.println("email:" +user);
        // Generate a random password reset token and OTP
        String resetToken = UUID.randomUUID().toString();
        String otp = generateOTP();
        System.out.println("toke:" +resetToken +"and otp:" +otp);
        // Set the expiration date for the token
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 24); // Set the expiration time (e.g., 24 hours)
        Date expiryDate = calendar.getTime();

        // Store the token, OTP, and expiration date in the database using Token entity
        Token passwordResetToken = new Token();
        passwordResetToken.setToken(resetToken);
        passwordResetToken.setType("password_reset"); // Set the type to identify password reset tokens
        passwordResetToken.setDateCreated(new java.sql.Date(new Date().getTime())); // Use java.sql.Date
        passwordResetToken.setExpiryDate(new java.sql.Date(expiryDate.getTime())); // Use java.sql.Date
        passwordResetToken.setUser(user.get()); // Get the user from the Optional
        passwordResetToken.setOtp(otp);
// Store the token in the database
        tokenRepository.save(passwordResetToken);


        // Send the OTP to the user's email
        try {
            sendOTPEmail(email, otp);
            return ResponseEntity.ok("OTP sent to your email");
        } catch (MailSendException e) {
            return ResponseEntity.badRequest().body("Failed to send OTP email");
        }
    }

    // Utility method to generate a random OTP (for demonstration purposes)
    private String generateOTP() {
        // Generate a 6-digit random OTP
        int otp = (int) (Math.random() * 900000) + 100000;
        return Integer.toString(otp);
    }

    // Utility method to send an OTP email using JavaMailSender
    // Utility method to send an OTP email using JavaMailSender
    private void sendOTPEmail(String toEmail, String otp) throws MailSendException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set the sender's email address
            helper.setFrom("kipkiruikenedy@gmail.com");

            // Set the email subject
            helper.setSubject("Password Reset OTP");

            // Set the recipient's email address
            helper.setTo(toEmail);

            // Create the HTML email content
            String emailContent = "<html><body>" +
                    "<h1>Password Reset OTP</h1>" +
                    "<p>Dear User,</p>" +
                    "<p>Your OTP for password reset is: <strong>" + otp + "</strong></p>" +
                    "<p>This OTP expires in 24 hours. Please do not share it with anyone.</p>" +
                    "<p>Thank you,</p>" +
                    "<p>Cytek Solar Limited Company Audit System</p>" +
                    "</body></html>";

            // Set the email content as HTML
            helper.setText(emailContent, true);

            // Send the email
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailSendException("Failed to send OTP email: " + e.getMessage());
        }
    }

}
