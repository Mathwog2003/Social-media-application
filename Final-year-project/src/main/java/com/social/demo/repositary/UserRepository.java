package com.social.demo.repositary;

import com.social.demo.entity.Post;
import com.social.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByEmailId(String emailId);
    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:query% OR u.lastName LIKE %:query% OR u.emailId LIKE %:query%")
    List<User> searchUser(@Param("query") String query);



}
