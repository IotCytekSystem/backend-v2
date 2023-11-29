package com.cytek2.cytek.audit.controller.cytekSubAdmins;

import com.cytek2.cytek.audit.model.Role;
import com.cytek2.cytek.audit.model.User;
import com.cytek2.cytek.audit.model.UserStatus;
import com.cytek2.cytek.audit.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CytekSubAdmins {
    private final UserRepository userRepository;

    public CytekSubAdmins(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @GetMapping("/subadmins/pending")
    public ResponseEntity<List<User>> PendingCytekSubAdmins() {
        List<User> users = userRepository.findByRoleAndUserStatus(Role.MANAGER, UserStatus.PENDING);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/subadmins/approved")
    public ResponseEntity<List<User>> ApprovedCytekSubAdmins() {
        List<User> users = userRepository.findByRoleAndUserStatus(Role.MANAGER, UserStatus.APPROVED);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/subadmins/archived")
    public ResponseEntity<List<User>> ArchivedCytekSubAdmins() {
        List<User> users = userRepository.findByRoleAndUserStatus(Role.MANAGER, UserStatus.ARCHIVED);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/subadmins/rejected")
    public ResponseEntity<List<User>> RejectedCytekSubAdmins() {
        List<User> users = userRepository.findByRoleAndUserStatus(Role.MANAGER, UserStatus.REJECTED);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/subadmins/approve/{userId}")
    public ResponseEntity<String> approveUser(@PathVariable Long userId) {
        try {
            // Find the user by ID
            User user = userRepository.findById(Math.toIntExact(userId)).orElse(null);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            // Update the user status to APPROVED
            user.setUserStatus(UserStatus.APPROVED);
            userRepository.save(user);

            return ResponseEntity.ok("User approved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while approving the user");
        }
    }

    @PostMapping("/subadmins/reject/{userId}")
    public ResponseEntity<String> rejectUser(@PathVariable Long userId) {
        try {
            // Find the user by ID
            User user = userRepository.findById(Math.toIntExact(userId)).orElse(null);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            // Update the user status to REJECTED
            user.setUserStatus(UserStatus.REJECTED);
            userRepository.save(user);

            return ResponseEntity.ok("User rejected successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while rejecting the user");
        }
    }
}
