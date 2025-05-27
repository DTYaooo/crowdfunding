package com.dtyao.crowdfunding.controller;

import com.dtyao.crowdfunding.model.Project;
import com.dtyao.crowdfunding.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController //类内所有返回值都不是页面，是JSON或者字符串，不写会当成页面路径跳转，出错
@RequestMapping("/api/project") //为所有接口加前缀
public class ProjectController {
    @Autowired //自动配置连接MongoDB
    private ProjectRepository projectRepository;

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
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        project.setUserId("testuser123"); // 模拟用户ID，方便调试
        project.setStatus("submitted"); //draft / submitted / recommended
        projectRepository.save(project); //存入MongoDB
        return "Submitted Successfully!";
    }

    /**
     *
     * @param userId the ID of the user
     * @return list of projects submitted by the user
     */
    @GetMapping("/list")
    public List<Project> getProjectsByUserId(@RequestParam String userId) {
        return projectRepository.findByUserId(userId);
    }
}

