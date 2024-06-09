package com.petito.project.dto.create;

import com.petito.project.entity.user.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserDto
{
    private String login;
    private String password;
    private String email;
    private User.Role role;
    private CreateDetailsDto details;
}
