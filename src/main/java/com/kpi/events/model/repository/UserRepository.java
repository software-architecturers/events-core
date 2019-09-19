package com.kpi.events.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kpi.events.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    User findByRefreshId(String refreshId);

    User findByLoginAndPassword(String login, String password);

    @Query(value = "from User where upper(login) like %:search% or upper(firstName) like %:search% " +
            "or upper(secondName) like %:search% or upper(email) like %:search%")
    List<User> searchUsers(String search, Pageable pageable);
}