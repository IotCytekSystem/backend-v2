package com.cytek2.cytek.audit.controller.auth;

import com.cytek2.cytek.audit.dto.RegisterRequest;
import com.cytek2.cytek.audit.model.Role;
import com.cytek2.cytek.audit.model.User;
import com.cytek2.cytek.audit.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserRegistrationController {

    private final UserRepository userRepository;




    public UserRegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest registerRequest) {
        try {
            // Check if the user with the same email already exists
            Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
            if (existingUser.isPresent()) {
                // Return a response indicating that the email is already taken
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }

            // Create a new User instance and populate it with data from registerRequest
            User newUser = new User();
            newUser.setName(registerRequest.getName());
            newUser.setCountry(registerRequest.getCountry());
            newUser.setCounty(registerRequest.getCounty());
            newUser.setTown(registerRequest.getTown());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPhone(registerRequest.getPhone());
            newUser.setPassword(registerRequest.getPassword());
            newUser.setRole(Role.valueOf("ADMIN"));

            // Save the user to the database
            User savedUser = userRepository.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            // Handle any unexpected exceptions or errors
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
