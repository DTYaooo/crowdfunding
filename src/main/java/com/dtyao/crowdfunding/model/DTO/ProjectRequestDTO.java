package com.dtyao.crowdfunding.model.DTO;

import jakarta.validation.constraints.*;
//3.x vertion

public class ProjectRequestDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Double goalAmount;
    @NotNull
    private Integer durationDays;
    @NotBlank
    private String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(Double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
