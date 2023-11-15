package com.bugtrackingsystem.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;

import com.bugtrackingsystem.entity.Project;
import com.bugtrackingsystem.entity.TestEngineer;
import com.bugtrackingsystem.exceptions.ResourceNotFoundException;
import com.bugtrackingsystem.repository.TestEngineerRepository;
import com.bugtrackingsystem.service.ITestEngineerService;

import java.util.List;

public class ITestEngineerServiceImplementation implements ITestEngineerService{
    @Autowired
    TestEngineerRepository testEngineerRepository;

    @Override
    public TestEngineer addTestEngineer(TestEngineer testEngineer) {
        return testEngineerRepository.save(testEngineer);
    }

    @Override
    public TestEngineer updateTestEngineer(TestEngineer testEngineer) {
        return testEngineerRepository.save(testEngineer);
    }

    @Override
    public TestEngineer getTestEngById(Long testerId) {
        return testEngineerRepository.findById(testerId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<TestEngineer> getAllTesters() {
        return testEngineerRepository.findAll();
    }

    @Override
    public Project getProjectByTestEngId(Long testEngId) {
        return getTestEngById(testEngId).getProject();
    }
}
