package com.dtyao.crowdfunding.controller;

import com.dtyao.crowdfunding.model.Project;
import com.dtyao.crowdfunding.repository.ProjectRepository;
import com.dtyao.crowdfunding.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.LocalDate;

@RestController //类内所有返回值都不是页面，是JSON或者字符串，不写会当成页面路径跳转，出错
@RequestMapping("/api/project") //为所有接口加前缀
public class ProjectController {
    @Autowired //自动配置连接MongoDB
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;

    /**
     * Submits a crowdfunding project.
     *
     * @param project the project submitted by the user, including title, description, goal amount, etc.
     * @return a success message string
     */
    @PostMapping("/submit")
    public String submitProject(@RequestBody Project project) {
        //controller操作：补全以下信息，然后进行数据保存
        project.setProjectId(UUID.randomUUID().toString());
        project.setCreatedAt(LocalDate.now());
        project.setUpdatedAt(LocalDate.now());
        project.setDeadline(projectService.calculateDdl(project.getCreatedAt(),project.getDurationDays()));
        project.setUserId("testuser123"); // 模拟用户ID，方便调试
        project.setStatus("submitted"); //draft / submitted / recommended
        projectRepository.save(project); //存入MongoDB
        return "Submitted Successfully!";
    }

    /**
     * Search the list of projects according to userId
     *
     * @param userId the ID of the user
     * @return list of projects submitted by the user
     */
    @GetMapping("/list")
    public List<Project> getProjectsByUserId(@RequestParam String userId) {
        return projectRepository.findByUserId(userId);
    }


    /**
     *Update the project
     *
     * @param projectId the ID of the project
     * @param updatedProject the project updated by users
     * @return a success message string
     */
    @PutMapping("/update/{projectId}")
    public String updateProject(
            @PathVariable String projectId,//从路径中提取projectId
            @RequestBody Project updatedProject){
        Project savedProject = projectRepository.findByProjectId(projectId).orElseThrow();
        //orElseThrow()找不到project会报错，但是是Optional内的方法，所以对象类型应该是Optional<Project>
        savedProject.setCategory(updatedProject.getCategory());
        savedProject.setTitle(updatedProject.getTitle());
        savedProject.setGoalAmount(updatedProject.getGoalAmount());
        savedProject.setDurationDays(updatedProject.getDurationDays());
        savedProject.setDescription(updatedProject.getDescription());
        savedProject.setUpdatedAt(LocalDate.now());
        savedProject.setDeadline(projectService.calculateDdl(savedProject.getCreatedAt(), updatedProject.getDurationDays()));
        projectRepository.save(savedProject);
        return "Updated Successfully!";
    }
    //不修改特定字段直接用.save(),updatedProject会完全覆盖savedProject，没有修改的字段会被覆盖成默认值或者为空

    /**
     * Delete the project
     *
     * @param projectId
     * @return a success message string
     */
    @DeleteMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable String projectId){
        projectRepository.deleteByProjectId(projectId);
        return "Deleted Successfully!";
    }
}

