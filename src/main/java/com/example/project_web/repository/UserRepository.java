package com.example.project_web.repository;

import com.example.project_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);

    User findUserById(int id);

    void deleteById(int id);

    User findByResetPasswordToken(String token);

    @Query(value = "SELECT * FROM User WHERE email = ?1", nativeQuery = true)
    public User findByEmail(String email);
}

