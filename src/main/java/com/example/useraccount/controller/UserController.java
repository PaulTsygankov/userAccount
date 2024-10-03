package com.example.useraccount.controller;

import com.example.useraccount.model.User;
import com.example.useraccount.service.UserService;
import com.example.useraccount.service.UserValidationService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    private final UserValidationService userValidationService;

    private final BCryptPasswordEncoder encoder;

    public UserController(UserService userService, UserValidationService userValidationService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.userValidationService = userValidationService;
        this.encoder = encoder;
    }

    @GetMapping
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(User user){
        return "registration";
    }

    public String administration(Model model){
        model.addAttribute("users", userService.findAllUsers());
        return "administration";
    }

    private String getAuthUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }



    // проверка на существование юзера
    private void validationCreateUsername(@Valid User user, BindingResult bindingResult){
        String message = userValidationService.createUserError(user);
        if(!message.isEmpty()){
            ObjectError objectError = new ObjectError("createUserError", message);
            bindingResult.addError(objectError);
        }
    }
    //проверка на валидный логин
    private void validationValidUsername(@Valid User user, BindingResult bindingResult){
        String message = userValidationService.isValidUsername(user);
        if(!message.isEmpty()){
            ObjectError objectError = new ObjectError("invalidUsername", message);
            bindingResult.addError(objectError);
        }
    }


    private void validationCreateEmail(@Valid User user, BindingResult bindingResult){
        String message = userValidationService.createEmailError(user);
        if(!message.isEmpty()){
            ObjectError objectError = new ObjectError("createEmailError", message);
            bindingResult.addError(objectError);
        }
    }
    private void validationValidEmail(@Valid User user, BindingResult bindingResult){
        String message = userValidationService.isSimpleValidEmail(user);
        if(!message.isEmpty()){
            ObjectError objectError = new ObjectError("invalidEmail", message);
            bindingResult.addError(objectError);
        }
    }


    private void validationPassword(@Valid User user, BindingResult bindingResult){
        String message = userValidationService.isValidPassword(user);
        if(!message.isEmpty()){
            ObjectError objectError = new ObjectError("invalidPassword", message);
            bindingResult.addError(objectError);
        }
    }
    private void validationValidPassword(@Valid User user, BindingResult bindingResult){
        String message = userValidationService.isValidPassword(user);
        if(!message.isEmpty()){
            ObjectError objectError = new ObjectError("invalidPassword", message);
            bindingResult.addError(objectError);
        }
    }
}
