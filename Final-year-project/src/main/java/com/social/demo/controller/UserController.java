package com.social.demo.controller;

import com.social.demo.entity.User;
import com.social.demo.repositary.UserRepository;
import com.social.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public User createUser(@RequestBody User user)
    {
        return userService.registerUser(user);
    }

   @PutMapping("/api/users/{id}")
   public User updateUser(@RequestBody User user, @PathVariable Integer id) throws Exception {
       return userService.updateUser(user,id);
   }

   @GetMapping("/api/users")
   public List<User> getUsers()
   {
       return userRepository.findAll();
   }
   @GetMapping("/api/users/{id}")
   public Optional<User> findUserById(@PathVariable Integer id) throws Exception {
       return userService.findUserById(id);
   }

   @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) throws Exception {
       Optional<User> user = userService.findUserById(id);
       if(user.isEmpty())
       {
           throw new Exception("users not found with this id" + id);
       }
       userService.deleteuser(id);
   }

   @PutMapping("/api/users/follow/{id1}/{id2}")
    public User followUserHandler(@PathVariable Integer id1,@PathVariable Integer id2) throws Exception {
       return userService.followUser(id1,id2);
   }

   @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam("query") String query)
   {
       return  userService.searchUser(query);
   }

   @GetMapping("/users/mail")
    public List<User> getUserByEmailId(@PathVariable String mail)
   {
       return (List<User>) userService.findUserByEmail(mail);
   }
}

