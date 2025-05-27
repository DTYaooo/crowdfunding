package com.dtyao.crowdfunding.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "projects")
@Data
public class Project {
    @Id
    private String projectId;
    private String title;
    private String description;
    private Double goalAmount;
    private int durationDays;
    private String category;
    private String deadline;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private String userId;
}
