package com.cytek2.cytek.audit.controller.auth;

import com.cytek2.cytek.audit.configurations.TokenGenerator;
import com.cytek2.cytek.audit.dto.AuthenticationRequest;
import com.cytek2.cytek.audit.dto.AuthenticationResponse; // Import AuthenticationResponse
import com.cytek2.cytek.audit.model.User;
import com.cytek2.cytek.audit.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;

    public LoginController(UserRepository userRepository, TokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest request) {
        System.out.println("Started login process...");

        // Find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = optionalUser.get();

        // Check if the provided password matches the stored password (plaintext for now)
        if (request.getPassword().equals(user.getPassword())) {
            // Generate and set access and refresh tokens here
            String accessToken = generateAccessToken(user);
            String refreshToken = generateRefreshToken(user);

            // Store the tokens in the user entity
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);

            // Save the updated user entity to the database
            userRepository.save(user);

            // Create an AuthenticationResponse with tokens and user details
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAccessToken(accessToken);
            authenticationResponse.setRefreshToken(refreshToken);
            authenticationResponse.setUser(user);

            // Return the AuthenticationResponse as JSON along with a success message
            return ResponseEntity.ok(authenticationResponse);
        } else {
            return ResponseEntity.badRequest().body("Wrong Username or password!");
        }
    }

    // Implement methods to generate access and refresh tokens as needed
    private String generateAccessToken(User user) {
        // Use the TokenGenerator to generate an access token
        return tokenGenerator.generateAccessToken();
    }

    private String generateRefreshToken(User user) {
        // Use the TokenGenerator to generate a refresh token
        return tokenGenerator.generateRefreshToken();
    }

}
