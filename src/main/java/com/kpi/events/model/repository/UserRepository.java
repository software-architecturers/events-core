package com.kpi.events.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpi.events.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);
}