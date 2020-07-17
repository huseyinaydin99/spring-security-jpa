package com.huseyinaydin.springsecurityjpa.com.huseyinaydin.service;

import com.huseyinaydin.springsecurityjpa.com.huseyinaydin.dao.UserRepository;
import com.huseyinaydin.springsecurityjpa.com.huseyinaydin.model.MyUserDetails;
import com.huseyinaydin.springsecurityjpa.com.huseyinaydin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //return new MyUserDetails(s);
        Optional<User> user = userRepository.findByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        user.map(user1 -> {
            System.err.println(user1.getUserName() + " " + user1.getPassword());
            UserDetails userDetails = new MyUserDetails(user1);
            MyUserDetails myUserDetails = new MyUserDetails(user1);
            return myUserDetails;
        });
        return user.map(MyUserDetails::new).get();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
