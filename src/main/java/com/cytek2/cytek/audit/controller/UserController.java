package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.dto.RegisterRequest;
import com.cytek2.cytek.audit.model.Meter;
import com.cytek2.cytek.audit.model.User;
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
    private final UserService userService;
    private final EnergyDataService energyDataService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, EnergyDataService energyDataService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.energyDataService = energyDataService;
    }

    // API to fetch all users whose role is "client" and show their number in the DB
    @GetMapping("/clients")
    public ResponseEntity<List<User>> getAllClients() {
        List<User> clients = userService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    // API to fetch user by ID
    @GetMapping("/clients/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // API to edit user
    @PutMapping("/clients/{userId}")
    public ResponseEntity<User> editUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Update 'name' field if it's provided in the request body
            if (updatedUser.getName() != null) {
                user.setName(updatedUser.getName());
            }

            // Update 'country' field if it's provided in the request body
            if (updatedUser.getCountry() != null) {
                user.setCountry(updatedUser.getCountry());
            }

            // Update 'county' field if it's provided in the request body
            if (updatedUser.getCounty() != null) {
                user.setCounty(updatedUser.getCounty());
            }

            // Update 'town' field if it's provided in the request body
            if (updatedUser.getTown() != null) {
                user.setTown(updatedUser.getTown());
            }

            // Update 'email' field if it's provided in the request body
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }

            // Update 'phone' field if it's provided in the request body
            if (updatedUser.getPhone() != null) {
                user.setPhone(updatedUser.getPhone());
            }

            // Update 'password' field if it's provided in the request body
            if (updatedUser.getPassword() != null) {
                user.setPassword(updatedUser.getPassword());
            }

            // Update 'role' field if it's provided in the request body
            if (updatedUser.getRole() != null) {
                user.setRole(updatedUser.getRole());
            }

            // Save the updated user
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        }
        return ResponseEntity.notFound().build();
    }



    // API to delete user (move to archive instead of removing from DB)
    @DeleteMapping("clients/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            userRepository.deleteById(user.getId());
            return ResponseEntity.ok("User Deleted Successfully");
        }

        return ResponseEntity.ok("Error Occured while trying to delete user, try again later");
    }

    // API to fetch all energy data belonging to a user
//    @GetMapping("/clients/energy-data")
//    public ResponseEntity<List<EnergyData>> getAllEnergyDataByUserId() {
//        List<EnergyData> energyDataList = energyDataService.getAllEnergyDataByUserId((long) );
//        if (energyDataList.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(energyDataList);
//    }

    // API to fetch all meters belonging to a user
    @GetMapping("clients/{userId}/meters")
    public ResponseEntity<List<Meter>> getMetersByUserId(@PathVariable Integer userId) {
        List<Meter> meters =  MeterRepository.findByUserId(userId);
        return ResponseEntity.ok(meters);
    }

    @PostMapping("/clients/add")
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
            newUser.setRole(registerRequest.getRole());

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
