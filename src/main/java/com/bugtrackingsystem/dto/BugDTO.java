package com.bugtrackingsystem.dto;

import com.bugtrackingsystem.entity.Bug;
import com.bugtrackingsystem.util.BugStatusEnum;
import com.bugtrackingsystem.util.SeverityEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugDTO {
    private String title;
    private String description;
    private SeverityEnum severity;
    private String comments;
    private Long createdByUserId;
    private Long projectId;
    private Long assignToId;

    @JsonIgnore
    public Bug toBugObject(){
        return new Bug(null,
                title,description,severity,comments,null,null,null, BugStatusEnum.NEW, LocalDateTime.now(),LocalDateTime.now());
    }
}
