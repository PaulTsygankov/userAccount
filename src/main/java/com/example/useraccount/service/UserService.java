package com.example.useraccount.service;

import com.example.useraccount.UserAccountApplication;
import com.example.useraccount.model.User;
import com.example.useraccount.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException(String.format("Пользователь не найден", username));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())));

    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public User findUserById(Long id){
        return userRepository.getOne(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public boolean existUser(User user){
        return userRepository.existsByUsername(user.getUsername());
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
