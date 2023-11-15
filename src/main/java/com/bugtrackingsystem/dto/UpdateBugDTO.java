package com.bugtrackingsystem.dto;

import com.bugtrackingsystem.entity.Bug;
import com.bugtrackingsystem.serviceimplementation.IBugServiceImplementation;
import com.bugtrackingsystem.util.BugStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBugDTO {
    private Long bugId;
    private String comments;
    private BugStatusEnum bugStatus;

    @JsonIgnore
    public Bug toBugObject(IBugServiceImplementation iBugServiceImplementation){
        Bug bug = iBugServiceImplementation.findBugById(bugId);
        bug.setComments(comments);
        bug.setBugStatus(bugStatus);
        bug.setLastUpdatedOn(LocalDateTime.now());
        return bug;
    }
}
