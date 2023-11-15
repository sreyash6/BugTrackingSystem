package com.bugtrackingsystem.entity;

import com.bugtrackingsystem.util.UserRoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
@Table(name="users_table")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private String skills;
    private UserRoleEnum role;
}
