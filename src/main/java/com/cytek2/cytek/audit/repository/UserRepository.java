package com.cytek2.cytek.audit.repository;


import com.cytek2.cytek.audit.model.Role;
import com.cytek2.cytek.audit.model.User;
import com.cytek2.cytek.audit.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


    List<User> findByRoleAndUserStatus(Role role, UserStatus userStatus);

}