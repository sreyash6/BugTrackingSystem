package com.bugtrackingsystem.service;


import org.springframework.stereotype.Service;

import com.bugtrackingsystem.entity.Project;
import com.bugtrackingsystem.entity.TestEngineer;

import java.util.List;
@Service
public interface ITestEngineerService {

	TestEngineer addTestEngineer(TestEngineer testEngineer);

	TestEngineer updateTestEngineer(TestEngineer testEngineer);

	TestEngineer getTestEngById(Long testerId);

	List<TestEngineer> getAllTesters();

	Project getProjectByTestEngId(Long testEngId);

}
