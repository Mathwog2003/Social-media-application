package com.social.demo.service;

import com.social.demo.entity.User;
import com.social.demo.repositary.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface{

    @Autowired
    private UserRepository userRepositary;
    @Override
    public User registerUser(User user) {
        return userRepositary.save(user);

    }

    @Override
    public User updateUser(User user, Integer userId) throws Exception {
        Optional<User> user1 = userRepositary.findById(userId);
        if(user1.isEmpty()) {
            throw new Exception("user not found with this Id " + userId);
        }

        User oldUser = user1.get();
        if(user.getFirstName()!=null)
        {
            oldUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName()!=null)
        {
            oldUser.setLastName(user.getLastName());
        }
        if(user.getEmailId()!=null)
        {
            oldUser.setEmailId(user.getEmailId());
        }

        User updateUser = userRepositary.save(oldUser);
        return updateUser;

    }

    @Override
    public List<User> searchUser(String query) {
        return userRepositary.searchUser(query);
    }

    @Override
    public Optional<User> findUserById(Integer id) throws Exception {
        Optional<User> user = userRepositary.findById(id);

        if(user.isPresent())
        {
            return Optional.of(user.get());
        }

        throw new Exception("user not exist with this id " + id);
    }

    @Override
    public User findUserByEmail(String emailId) {
        return userRepositary.findByEmailId(emailId);
    }



    @Override
    public User followUser(Integer userId1, Integer userId2) throws ChangeSetPersister.NotFoundException
    {
            User user1 = userRepositary.findById(userId1).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
            User user2 = userRepositary.findById(userId2).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

            if (!user2.getFollowers().contains(userId1)) {
                user2.getFollowers().add(userId1);
            }

            if (!user1.getFollowings().contains(userId2)) {
                user1.getFollowings().add(userId2);
            }

            // Manually handle the transaction
            userRepositary.save(user1);
            userRepositary.save(user2);

            return user1;
        }

    @Override
    public String deleteuser(Integer id) {
         userRepositary.deleteById(id);
         return "user deleted successfully with the id" + id;
    }
}


