package com.dtyao.crowdfunding.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Project Model
 */
@Document(collection = "projects")
@Data
public class Project {
    /**
     * required
     */
    private String title;
    private String description;
    private Double goalAmount;
    private int durationDays;
    private String category;

    /**
     * system-generated
     */
    @Id
    private String projectId;
    private LocalDate deadline;
    private String status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String userId;
}
