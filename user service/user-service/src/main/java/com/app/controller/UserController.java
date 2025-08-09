package com.app.controller;

import com.app.model.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/users")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/api/users")
    public List<User> getUser(){
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable("id")Long id) throws Exception{

        Optional<User> otp = userRepository.findById(id);

        if(otp.isPresent()){
            return otp.get();
        }
        throw  new Exception(("User not found Given Application Id"));

        

    }

    @PutMapping("/api/users/{id}")
    public User updateUser(@PathVariable("id")Long id,@RequestBody User user) throws Exception {
        Optional<User> otp = userRepository.findById(id);

        if(otp.isEmpty()){
            throw new Exception("User not found with Id "+ id);
        }else{
            User exUser = otp.get();

            exUser.setFullName(user.getFullName());
            exUser.setEmail(user.getEmail());
            exUser.setRole(user.getRole());

            return userRepository.save(exUser);
        }

    }

    @DeleteMapping("/api/users/{id}")
    public String deleteUserById(@PathVariable("id")Long id) throws Exception{

        Optional<User> otp = userRepository.findById(id);

        if(otp.isPresent()){
            userRepository.deleteById(otp.get().getId());
            return "User have been removed";
        }
        throw  new Exception(("User not found Given Application Id"));



    }


}
