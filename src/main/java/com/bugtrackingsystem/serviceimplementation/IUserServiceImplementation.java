package com.bugtrackingsystem.serviceimplementation;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugtrackingsystem.entity.Developer;
import com.bugtrackingsystem.entity.TestEngineer;
import com.bugtrackingsystem.entity.User;
import com.bugtrackingsystem.exceptions.ResourceAlreadyExistException;
import com.bugtrackingsystem.exceptions.ResourceNotFoundException;
import com.bugtrackingsystem.repository.DeveloperRepository;
import com.bugtrackingsystem.repository.TestEngineerRepository;
import com.bugtrackingsystem.repository.UserRepository;
import com.bugtrackingsystem.service.IUserService;
import com.bugtrackingsystem.util.UserRoleEnum;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Slf4j
public class IUserServiceImplementation implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private TestEngineerRepository testEngineerRepository;
    @Autowired
    private DeveloperRepository developerRepository;

    @Transactional
    public User registerUser(User user ){
        user.setId(null);
        if(userRepository.existsByEmail(user.getEmail())) throw new ResourceAlreadyExistException("User already preset");
        userRepository.save(user);
        if(UserRoleEnum.TESTER.equals(user.getRole())){
            testEngineerRepository.save(new TestEngineer(null,user.getFirstName()+" "+user.getLastName(), user.getSkills(),null,user));
        } else if (UserRoleEnum.DEVELOPER.equals(user.getRole())) {
            developerRepository.save(new Developer(null,user.getFirstName()+" "+user.getLastName(),user.getSkills(),null, user));
        }
        return user;
    }

    public User signIn(User user){

        return userRepository.findByEmailIgnoreCaseAndPassword(user.getEmail(), user.getPassword()).orElseThrow(
                () -> new ResourceNotFoundException("Wrong credentials, Please try again")
        );
    }

    @Override
    public String signOut() {
        return "Sign Out Successfull";
    }

    public List<User> findOtherUsers(Long userId){
        return userRepository.findByIdNot(userId);
    }

    public List<User> findAllByRole(UserRoleEnum role){
        return userRepository.findByRole(role);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public String greet(){
        return "greeting";
    }
}
