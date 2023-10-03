package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.dto.RegisterRequest;
import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.model.Meter;
import com.cytek2.cytek.audit.model.User;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import com.cytek2.cytek.audit.repository.MeterRepository;
import com.cytek2.cytek.audit.repository.UserRepository;
import com.cytek2.cytek.audit.services.UserService;
import com.cytek2.cytek.audit.services.service.EnergyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {



    private final UserRepository userRepository;

    @Autowired
    private MeterRepository meterRepository;


    private final UserService userService;
    private final EnergyDataService energyDataService;

     @Autowired
    private EnergyDataRepository energyDataRepository;

    public UserController(UserRepository userRepository, UserService userService, EnergyDataService energyDataService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.energyDataService = energyDataService;
    }


    //     API to fetch all users whose role is "client" and show their number in the DB
    @GetMapping("/clients")
    public ResponseEntity<List<User>> getAllClients() {
        List<User> clients = userService.getAllClients();
        return ResponseEntity.ok(clients);
    }


    // API to fetch user by ID
    @GetMapping("/clients/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }


    // API to edit user
    @PutMapping("/clients/{userId}")
    public User editUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // Update user properties here

            user.setEmail(updatedUser.getEmail());
            // Update other properties as needed
            userRepository.save(user);
        }
        return user;
    }

    // API to delete user (move to archive instead of removing from DB)
    @DeleteMapping("clients/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // Move user to archive or perform archiving logic
            // Instead of deleting, you can update a flag like "archived" in the user entity
            user.setArchived(true);
            userRepository.save(user);
        }
    }
    // API to fetch all energy data belonging to a user
    private static final int clientID = 1;
    @GetMapping("/clients/energy-data")
    public ResponseEntity<List<EnergyData>> getAllEnergyDataByUserId() {
        List<EnergyData> energyDataList = energyDataService.getAllEnergyDataByUserId((long) clientID);

        if (energyDataList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(energyDataList);
    }




    // API to fetch all meters belonging to a user
    @GetMapping("clients/{userId}/meters")
    public List<Meter> getMetersByUserId(@PathVariable Long userId) {
        return meterRepository.findByUserId(Math.toIntExact(userId));
    }




    @PostMapping("/clients/add")
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest registerRequest) {
        try {
            // Add detailed logging here to capture request data
            System.out.println("Received addUser request: " + registerRequest.toString());

            // Check if the user with the same email already exists
            Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());

            if (existingUser.isPresent()) {
                // Return a response indicating that the email is already taken
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }

            // Validation passed, save the new user to the database
            User newUser = new User(); // Create a new User instance
            // Populate the newUser object with data from registerRequest
            newUser.setName(registerRequest.getName());
            newUser.setCountry(registerRequest.getCountry());
            newUser.setCounty(registerRequest.getCounty());
            newUser.setTown(registerRequest.getTown());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPhone(registerRequest.getPhone()); // Fixed a typo here
            newUser.setPassword(registerRequest.getPassword());
            newUser.setRole(registerRequest.getRole());

            // Save the user to the database
            User savedUser = userRepository.save(newUser);

            // Log success and return the saved user
            System.out.println("User saved successfully: " + savedUser.toString());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();

            // Handle any unexpected exceptions or errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }




}
