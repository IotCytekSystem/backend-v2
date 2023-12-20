package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.dto.AddClientRequest;
import com.cytek2.cytek.audit.model.*;
import com.cytek2.cytek.audit.repository.MeterRepository;
import com.cytek2.cytek.audit.repository.UserRepository;
import com.cytek2.cytek.audit.services.UserService;
import com.cytek2.cytek.audit.services.service.EnergyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final UserRepository userRepository;
    private final UserService userService;

    private final MeterRepository meterRepository;

    @Autowired
    public ClientController(UserRepository userRepository, UserService userService, EnergyDataService energyDataService, MeterRepository meterRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.meterRepository = meterRepository;
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
            if (updatedUser.getFirstname() != null) {
                user.setFirstname(updatedUser.getFirstname());
            }
            if (updatedUser.getLastname()!= null) {
                user.setLastname(updatedUser.getLastname());
            }
            // Update 'email' field if it's provided in the request body
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }

            // Update 'phone' field if it's provided in the request body
            if (updatedUser.getPhone() != null) {
                user.setPhone(updatedUser.getPhone());
                user.setPassword(updatedUser.getPhone());
            }


            // Update 'country' field if it's provided in the request body
            if (updatedUser.getCountry() != null) {
                user.setCountry(updatedUser.getCountry());
            }

            // Update 'county' field if it's provided in the request body
            if (updatedUser.getLocation() != null) {
                user.setLocation(updatedUser.getLocation());
            }

            // Update 'town' field if it's provided in the request body
            if (updatedUser.getTown() != null) {
                user.setTown(updatedUser.getTown());
            }



            // Update 'role' field
                user.setRole(Role.valueOf("CLIENT"));


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
    public ResponseEntity<?> addUser(@RequestBody AddClientRequest addClientRequest) {
        try {
            // Check if the user with the same email already exists
            Optional<User> existingUser = userRepository.findByEmail(addClientRequest.getEmail());
            if (existingUser.isPresent()) {
                // Return a response indicating that the email is already taken
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }



            // Create a new User instance and populate it with data from addClientRequest
            User newUser = new User();
            newUser.setFirstname(addClientRequest.getFirstname());
            newUser.setLastname(addClientRequest.getLastname());
            newUser.setEmail(addClientRequest.getEmail());
            newUser.setPhone(addClientRequest.getPhone());
            newUser.setCountry(addClientRequest.getCountry());
            newUser.setTown(addClientRequest.getTown());
            newUser.setLocation(addClientRequest.getLocation());
            newUser.setPassword(addClientRequest.getPhone());
            newUser.setManager(addClientRequest.getManager());
            newUser.setMeterId(addClientRequest.getMeterId());
            newUser.setRole(Role.valueOf("CLIENT"));
            newUser.setUserStatus(UserStatus.valueOf("APPROVED"));

            // Save the user to the database
            User savedUser = userRepository.save(newUser);

            if (savedUser.getMeterId() != null) {
                Optional<Meter> optionalMeter = meterRepository.findById(Long.valueOf(savedUser.getMeterId()));
                if (optionalMeter.isPresent()) {
                    Meter meter = optionalMeter.get();
                    meter.setUserId(savedUser.getId());
                    meter.setMeterStatus(MeterStatus.ACTIVE);

                    meterRepository.save(meter);
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            // Handle any unexpected exceptions or errors
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while trying to save Client");
        }
    }

    @PostMapping("/client/archive/{userId}")
    public ResponseEntity<String> rejectUser(@PathVariable Integer userId) {
        try {
            // Find the user by ID
            User user = userRepository.findById(userId).orElse(null);

            if (user != null) {
                // Update the user status to REJECTED
                user.setUserStatus(UserStatus.ARCHIVED);
                userRepository.save(user);
                Meter meter = meterRepository.findMeterByUserId(userId);
                if (meter != null) {
                    try {
                        // Update the Meter entity
                        meter.setUserId(null);
                        meter.setMeterStatus(MeterStatus.IDLE);
                        meterRepository.save(meter);

                    }
                    catch (Exception e) {
                    return ResponseEntity.status(500).body("meter not updated");
                }

                }
            }
            return ResponseEntity.ok("User Archived successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while rejecting the user");
        }
    }

}
