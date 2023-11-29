package com.cytek2.cytek.audit.services;

import com.cytek2.cytek.audit.model.Role;
import com.cytek2.cytek.audit.model.User;
import com.cytek2.cytek.audit.model.UserStatus;
import com.cytek2.cytek.audit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllClients() {

        return userRepository.findByRoleAndUserStatus(Role.CLIENT, UserStatus.APPROVED);
    }

    public void deleteById(User user) {
    }
}
