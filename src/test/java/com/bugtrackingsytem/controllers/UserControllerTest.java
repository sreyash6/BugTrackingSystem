package com.bugtrackingsytem.controllers;

import com.bugtrackingsystem.BugTrackingSystemApplication;
import com.bugtrackingsystem.dto.UserDTO;
import com.bugtrackingsystem.entity.User;
import com.bugtrackingsystem.exceptions.ResourceNotFoundException;
import com.bugtrackingsystem.serviceimplementation.IUserServiceImplementation;
import com.bugtrackingsystem.util.UserRoleEnum;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BugTrackingSystemApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserServiceImplementation iUserServiceImplementation;

    private List<User> userList;
    Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(1L, "John","Weak","admin@gmail.com", "pwd1",null, UserRoleEnum.ADMIN));
        this.userList.add(new User(2L,"Tony","Stark", "developer@gmail.com", "pwd2","Java",UserRoleEnum.DEVELOPER));
        this.userList.add(new User(3L,"Black","Widow" ,"tester@gmail.com", "pwd3","Manual Testing, Chrom Debug",UserRoleEnum.TESTER));
    }

    @Test
    void register() throws Exception {
        var user = new User(3L,"Black","Widow" ,"tester@gmail.com", "pwd3","Auto Testing",UserRoleEnum.TESTER);
        var userDto = new UserDTO("Black","Widow" ,"tester@gmail.com", "pwd3", UserRoleEnum.TESTER, "AB Testing");

        when(iUserServiceImplementation.registerUser(userDto.toUserObject())).thenReturn(user);

        this.mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id",is(3)));
    }

    @Test
    void login() throws Exception {
        var user = new User(3L,"Black","Widow" ,"tester@gmail.com", "pwd3","AB Testing",UserRoleEnum.TESTER);
        UserDTO userDTO = new UserDTO(null, null,"tester@gmail.com", "pwd3", null,"AB Testing");
        UserDTO userDTOWrong = new UserDTO(null, null,"tester@gmail.com", "pwd3eeeee", null,null);
        when(iUserServiceImplementation.signIn(userDTO.toUserObject())).thenReturn(user);
        when(iUserServiceImplementation.signIn(userDTOWrong.toUserObject())).thenThrow(new ResourceNotFoundException());

        //Should return 200
        this.mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(userDTO))).andDo(print()).andExpect(status().isOk());
        //Should return 404
        this.mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(userDTOWrong))).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void otherUsers() throws Exception {
        userList.remove(2);
        when(iUserServiceImplementation.findOtherUsers(3L)).thenReturn(userList);

        this.mockMvc.perform(get("/api/user/otherUsers/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(2)));
    }

    @Test
    void findAllByRole() throws Exception {
        userList.removeIf(item-> !item.getRole().equals(UserRoleEnum.TESTER));
        when(iUserServiceImplementation.findAllByRole(UserRoleEnum.TESTER)).thenReturn(userList);
        this.mockMvc.perform(get("/api/user/allByRole/TESTER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(1)));
    }

    @Test
    void findAllUsers() throws Exception {
        when(iUserServiceImplementation.findAll()).thenReturn(userList);
        this.mockMvc.perform(get("/api/user/allUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(3)));
    }
}