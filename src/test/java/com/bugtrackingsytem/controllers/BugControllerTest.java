package com.bugtrackingsytem.controllers;

import com.bugtrackingsystem.BugTrackingSystemApplication;
import com.bugtrackingsystem.dto.BugDTO;
import com.bugtrackingsystem.entity.Bug;
import com.bugtrackingsystem.entity.Project;
import com.bugtrackingsystem.entity.User;
import com.bugtrackingsystem.serviceimplementation.IBugServiceImplementation;
import com.bugtrackingsystem.util.BugStatusEnum;
import com.bugtrackingsystem.util.SeverityEnum;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BugTrackingSystemApplication.class)
@AutoConfigureMockMvc
class BugControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    IBugServiceImplementation iBugServiceImplementation;
    private List<Bug> bugs;
    Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "John","Weak","admin@gmail.com", "pwd1", null,UserRoleEnum.ADMIN));
        userList.add(new User(2L,"Tony","Stark", "developer@gmail.com", "pwd2","Java",UserRoleEnum.DEVELOPER));
        userList.add(new User(3L,"Black","Widow" ,"tester@gmail.com", "pwd3","Auto Testing",UserRoleEnum.TESTER));

        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "American Express UI", "ONGOING","Web Portal"));
        projects.add(new Project(2L, "Alibaba", "COMPLETED","ANDROID"));
        projects.add(new Project(3L, "MAX", "ONGOING","Web Portal"));

        bugs = new ArrayList<>();
        bugs.add(new Bug(1L,"Color Correction","Change color",
                SeverityEnum.MEDIUM,null,
                userList.get(0),userList.get(1),
                projects.get(0), BugStatusEnum.NEW,
                LocalDateTime.now().minusDays(1L), LocalDateTime.now()));
        bugs.add(new Bug(1L,"Black Screen","Harware Firmware",
                SeverityEnum.MEDIUM,"Firm Changed",
                userList.get(2),userList.get(1),
                projects.get(2), BugStatusEnum.CLOSED,
                LocalDateTime.now().minusDays(1L), LocalDateTime.now()));
    }

    @Test
    void createBug() throws Exception {
        BugDTO bugDTO = new BugDTO("Black Screen","Harware Firmware",
                SeverityEnum.MEDIUM,"Firm Changed", 1L,1L,0L);
        when(iBugServiceImplementation.createBug(bugDTO)).thenReturn(bugs.get(1));
        this.mockMvc.perform(post("/api/bug/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(bugDTO))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id",is(1)))
                .andExpect(jsonPath("$.data.title",is("Black Screen")));
    }

    @Test
    void allBugs() throws Exception {
        when(iBugServiceImplementation.findAllBugs()).thenReturn(bugs);
        this.mockMvc.perform(get("/api/project/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(4)));
    }
}