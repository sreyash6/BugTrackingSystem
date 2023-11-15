package com.bugtrackingsystem.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bugtrackingsystem.entity.Developer;
import com.bugtrackingsystem.entity.Project;
import com.bugtrackingsystem.entity.TestEngineer;
import com.bugtrackingsystem.exceptions.ResourceNotFoundException;
import com.bugtrackingsystem.repository.DeveloperRepository;
import com.bugtrackingsystem.repository.ProjectRepository;
import com.bugtrackingsystem.repository.TestEngineerRepository;
import com.bugtrackingsystem.service.IProjectService;

import java.util.List;

@Service
public class IProjectServiceImplementation  implements IProjectService{
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    private DeveloperRepository developerRepository;
    @Autowired
    private TestEngineerRepository testEngineerRepository;

    public Project createProject(Project project){
        project.setId(null);
        projectRepository.save(project);
        return project;
    }


    @Override
    public Project getProjectById(Long projId) {
        return projectRepository.findById(projId).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    @Override
    public Project updateProject(Project projDTO) {
        return projectRepository.save(projDTO);
    }

    @Override
    public List<Developer> getDevelopersByProjectId(Long projId) {
        return developerRepository.findByProject_Id(projId);
    }

    @Override
    public List<TestEngineer> getTestEngineersByProjectId(Long projId) {
        return testEngineerRepository.findByProject_Id(projId);
    }
}
