package com.cytek2.cytek.audit.controller.auth;

import com.cytek2.cytek.audit.model.User;
import com.cytek2.cytek.audit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LogoutController {

    private final UserRepository userRepository;

    @Autowired
    public LogoutController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestParam("email") String userEmail) {
        // Find the user by email
        Optional<User> Optionaluser = userRepository.findByEmail(userEmail);

        if (Optionaluser.isPresent()) {
            // Clear the access and refresh tokens
            User user =Optionaluser.get();
            user.setAccessToken(null);
            user.setRefreshToken(null);

            // Save the updated user entity to the database
            userRepository.save(user);

            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }
}
