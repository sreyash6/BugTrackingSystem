package com.bugtrackingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bugtrackingsystem.dto.BasicDTO;
import com.bugtrackingsystem.dto.BugDTO;
import com.bugtrackingsystem.dto.SearchDTO;
import com.bugtrackingsystem.dto.UpdateBugDTO;
import com.bugtrackingsystem.entity.Bug;
import com.bugtrackingsystem.entity.BugHistory;
import com.bugtrackingsystem.serviceimplementation.IBugServiceImplementation;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/bug")
public class BugController {
    @Autowired
    IBugServiceImplementation iBugServiceImplementation;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/create")
    public ResponseEntity<BasicDTO<Bug>> createBug(@RequestBody BugDTO bugDTO){
        return new ResponseEntity(new BasicDTO<>(iBugServiceImplementation.createBug(bugDTO)), HttpStatus.CREATED);
    }
    @PatchMapping("/update")
    public ResponseEntity<BasicDTO<Bug>> updateBugStatus(@RequestBody UpdateBugDTO updateBugDTO){
        return ResponseEntity.ok(new BasicDTO<>(iBugServiceImplementation.updateBug(updateBugDTO)));
    }
    @GetMapping("/myAssignedBugs/{userId}")
    public ResponseEntity<BasicDTO<List<Bug>>> myAssignedBugs(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(new BasicDTO<>(iBugServiceImplementation.myAssignedBugs(userId)));
    }

    @PostMapping("/search")
    public ResponseEntity<BasicDTO<List<Bug>>> searchBug(@RequestBody SearchDTO searchDTO){
        return ResponseEntity.ok(new BasicDTO<>(iBugServiceImplementation.searchBug(searchDTO)));
    }
    @GetMapping("/bugHistory/{bugId}")
    public ResponseEntity<BasicDTO<List<BugHistory>>> findBugHistoryById(@PathVariable("bugId") Long bugId){
        return ResponseEntity.ok(new BasicDTO<>(iBugServiceImplementation.findBugHistoryById(bugId)));
    }
    @GetMapping("/myCreatedBugs/{userId}")
    public ResponseEntity<BasicDTO<List<Bug>>> myCreatedBugs(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(new BasicDTO<>(iBugServiceImplementation.myCreatedBugs(userId)));
    }
    @GetMapping("/projectBugs/{projectId}")
    public ResponseEntity<BasicDTO<List<Bug>>> projectBugs(@PathVariable("projectId") Long projectId){
        return ResponseEntity.ok(new BasicDTO<>(iBugServiceImplementation.findAllBugsByProjectId(projectId)));
    }
    @GetMapping("/all")
    public ResponseEntity<BasicDTO<List<Bug>>> allBugs(){
        return ResponseEntity.ok(new BasicDTO<>(iBugServiceImplementation.findAllBugs()));
    }
}
