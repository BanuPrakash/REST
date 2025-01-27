package com.adobe.orderapp.security.dao;


import com.adobe.orderapp.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
