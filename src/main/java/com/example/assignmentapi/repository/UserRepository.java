package com.example.assignmentapi.repository;

import com.example.assignmentapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
}
