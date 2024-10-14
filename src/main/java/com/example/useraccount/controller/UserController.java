package com.example.userAccount.controller;

import com.example.userAccount.model.RoleProfil;
import com.example.userAccount.model.User;
import com.example.userAccount.service.UserService;
import com.example.userAccount.service.UserValidationService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationForm(@Valid User user, BindingResult bindingResult){
        allValidation(user,bindingResult);
        if (bindingResult.hasErrors()){
            return "registration";
        }
        user.setRole(RoleProfil.USER.getRoleProfil());
        String pass = encoder.encode(user.getPassword());
        user.setPassword(pass);
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/home")
    public String home(Model model){
        User user = userService.findByUsername(getAuthUsername());
        model.addAttribute("user", userService.findByUsername(user.getUsername()));
        return "home";
    }

    @GetMapping("/page")
    public String page(Model model){
        User userFrDB = userService.findByUsername(getAuthUsername());
        model.addAttribute("user", userFrDB);
        return "page";
    }

    @GetMapping("/administration")
    public String administration(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "administration";
    }

    private String getAuthUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private void validationPassword(@Valid User user, BindingResult bindingResult) {
        String message = userValidationService.isValidPassword(user);
        if (!message.isEmpty()) {
            ObjectError objectError = new ObjectError("invalidPassword", message);
            bindingResult.addError(objectError);
        }
    }

    private void validationValidPassword(@Valid User user, BindingResult bindingResult) {
        String message = userValidationService.minLengthPassword(user);

        if (!message.isEmpty()) {
            ObjectError objectError = new ObjectError("isEmptyPass", message);
            bindingResult.addError(objectError);
        }
    }

    private void validationCreateUsername(@Valid User user, BindingResult bindingResult) {
        String message = userValidationService.createUserError(user);

        if (!message.isEmpty()) {
            ObjectError objectError = new ObjectError("createUserError", message);
            bindingResult.addError(objectError);
        }
    }

    private void validationValidUsername(@Valid User user, BindingResult bindingResult) {
        String message = userValidationService.isValidUsername(user);

        if (!message.isEmpty()) {
            ObjectError objectError = new ObjectError("invalidUsername", message);
            bindingResult.addError(objectError);
        }
    }

    private void validationCreateEmail(@Valid User user, BindingResult bindingResult) {
        String message = userValidationService.createEmailError(user);

        if (!message.isEmpty()) {
            ObjectError objectError = new ObjectError("createEmailError", message);
            bindingResult.addError(objectError);
        }
    }


    private void validationValidEmail(@Valid User user, BindingResult bindingResult) {
        String message = userValidationService.isSimpleValidEmail(user);

        if (!message.isEmpty()) {
            ObjectError objectError = new ObjectError("invalidEmail", message);
            bindingResult.addError(objectError);
        }
    }

    private void allValidation(@Valid User user, BindingResult bindingResult){
        validationCreateUsername(user, bindingResult);
        validationValidUsername(user, bindingResult);
        validationCreateEmail(user,bindingResult);
        validationValidEmail(user,bindingResult);
        validationPassword(user,bindingResult);
        validationValidPassword(user,bindingResult);
    }

}
