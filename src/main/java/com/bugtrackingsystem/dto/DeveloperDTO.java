package com.bugtrackingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor @AllArgsConstructor
public class DeveloperDTO {

	private String name;
	private String skill;
	private Long projectId;
	private Long userId;

}