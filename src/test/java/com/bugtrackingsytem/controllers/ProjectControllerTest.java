package com.bugtrackingsytem.controllers;

import com.bugtrackingsystem.BugTrackingSystemApplication;
import com.bugtrackingsystem.entity.Project;
import com.bugtrackingsystem.serviceimplementation.IProjectServiceImplementation;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BugTrackingSystemApplication.class)
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    IProjectServiceImplementation iProjectServiceImplementation;

    Gson gson = new Gson();
    private List<Project> projects;
    @BeforeEach
    void setUp() {
        this.projects = new ArrayList<>();
        projects.add(new Project(1L, "American Express UI", "ONGOING","Web Portal"));
        projects.add(new Project(2L, "Alibaba", "COMPLETED","ANDROID"));
        projects.add(new Project(3L, "MAX", "ONGOING","Web Portal"));
        projects.add(new Project(4L, "Zudio", "WATING","iOS App"));
    }

    @Test
    void createProject() throws Exception {
        var projectReq = new Project(null, "American Express UI", "ONGOING","Web Portal");
        when(iProjectServiceImplementation.createProject(projectReq)).thenReturn(projects.get(0));
        this.mockMvc.perform(post("/api/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(projectReq))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id",is(1)));
    }

    @Test
    void allProjects() throws Exception {
        when(iProjectServiceImplementation.getAllProjects()).thenReturn(projects);
        this.mockMvc.perform(get("/api/project/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(4)));
    }
}