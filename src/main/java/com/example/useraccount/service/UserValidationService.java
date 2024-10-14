package com.example.userAccount.service;

import com.example.userAccount.model.User;
import com.example.userAccount.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserValidationService {
    private final UserRepository userRepository;

    @Autowired
    public UserValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUserError(User user) {
        String message = "";
        if (userRepository.existsByUsername(user.getUsername())) {
            message = "Пользователь с таким именем существует";
        }
        return message;
    }

    public String createEmailError(User user) {
        String message = "";
        if (userRepository.existsByEmail(user.getEmail())) {
            message = "Пользователь с такой почтой уже существует";
        }
        return message;
    }

    public String isValidUsername(User user) {
        String message = "";
        String regex = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getUsername());
        if (!matcher.matches()) {
            message = "Некорректный логин";
        }
        return message;
    }

    public String isValidPassword(User user) {
        String message = "";
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            message = "Пароли не совпадают";
        }
        return message;
    }

    public String minLengthPassword(User user) {
        String message = "";
        if (user.getPassword().length() < 4) {
            message = "Минимальная длина пароля составляет 4 символа";
        }
        return message;
    }

    public String isSimpleValidEmail(User user) {
        String message = "";
        String regex = "^(.+)@(\\S+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()){
            message = "Некорретный Email";
        }
        return message;
    }
}
