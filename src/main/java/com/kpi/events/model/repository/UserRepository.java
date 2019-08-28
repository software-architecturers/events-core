package com.kpi.events.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpi.events.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    
    User findByRefreshId(String refreshId);

    User findByLoginAndPassword(String login, String password);
}