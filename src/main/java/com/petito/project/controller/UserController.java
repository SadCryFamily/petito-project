package com.petito.project.controller;

import com.petito.project.service.SmtpService;
import com.petito.project.dto.create.CreateUserDto;
import com.petito.project.entity.user.Details;
import com.petito.project.entity.user.User;
import com.petito.project.repository.UserRepository;
import com.petito.project.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController
{
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final SmtpService smtpService;
    private final PasswordEncoder passwordEncoder;

    @InitBinder("user")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(userValidator);
    }

    @GetMapping("/register")
    public String registerUserPage(Model model)
    {
        model.addAttribute("user", new CreateUserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid CreateUserDto user,
                               BindingResult result)
    {
        if (result.hasErrors())
        {
            return "register";
        }

        Details solidDetails = Details.builder()
                .firstName(user.getDetails().getFirstName())
                .lastName(user.getDetails().getLastName())
                .numberPhone(user.getDetails().getNumberPhone())
                .build();

        User solidUser = User.builder()
                .login(user.getLogin())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .role(User.Role.USER)
                .build();

        solidDetails.setUser(solidUser);
        solidUser.setDetails(solidDetails);

        userRepository.save(solidUser);

        smtpService.registrationNotify(user.getEmail(), solidDetails.getFirstName());

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage()
    {
        return "logout";
    }
}
