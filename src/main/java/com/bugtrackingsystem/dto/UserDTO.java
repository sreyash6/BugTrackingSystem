package com.bugtrackingsystem.dto;

import com.bugtrackingsystem.entity.User;
import com.bugtrackingsystem.util.UserRoleEnum;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRoleEnum role;
    private String skills;

    public User toUserObject(){
        return new User(null, firstName, lastName,email, password,skills,role);
    }
}
