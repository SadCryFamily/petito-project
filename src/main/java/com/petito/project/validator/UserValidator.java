package com.petito.project.validator;

import com.petito.project.constants.SQLFieldConstants;
import com.petito.project.dto.create.CreateUserDto;
import com.petito.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return CreateUserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        CreateUserDto user = (CreateUserDto) target;

        validateLogin(errors, user.getLogin());
        validatePassword(errors, user.getPassword());
    }

    private void validateLogin(Errors errors, String login)
    {
        if (userRepository.existsByLogin(login))
        {
            errors.rejectValue("login", "register.error.login-duplicate");
        }

        if (SQLFieldConstants.isCorrectTextField(login))
        {
            errors.rejectValue("login", "register.error.login-size");
        }
    }

    private void validatePassword(Errors errors, String password)
    {
        rejectIfEmptyOrWhitespace(errors, "password", "register.error.password-empty");

        if (!SQLFieldConstants.isCorrectPassword(password)) {
            errors.rejectValue("password", "register.error.password-size");
        }
    }
}
