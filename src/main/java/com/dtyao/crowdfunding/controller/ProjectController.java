package com.dtyao.crowdfunding.controller;

import com.dtyao.crowdfunding.model.DTO.ProjectRequestDTO;
import com.dtyao.crowdfunding.model.DTO.ProjectResponseDTO;
import com.dtyao.crowdfunding.model.DTO.ProjectUpdateDTO;
import com.dtyao.crowdfunding.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController //类内所有返回值都不是页面，是JSON或者字符串，不写会当成页面路径跳转，出错
@RequestMapping("/api/project") //为所有接口加前缀
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/submit")
    public ResponseEntity<ProjectResponseDTO> submitProject(@RequestBody ProjectRequestDTO requestDTO) {
       ProjectResponseDTO responseDTO = projectService.submitProject(requestDTO);
       return ResponseEntity.ok(responseDTO);
       /*
       直接返回ProjectResponseDTO可以运行但不能设置状态码和响应头
        */
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByUserId(@RequestParam String userId) {
        List<ProjectResponseDTO> projectList = projectService.getProjectsByUserId(userId);
        return ResponseEntity.ok(projectList);
    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable String projectId,//从路径中提取projectId
            @RequestBody ProjectUpdateDTO updatedDTO){
       ProjectResponseDTO responseDTO = projectService.updateProject(projectId, updatedDTO);
       return ResponseEntity.ok(responseDTO);
    }
    //不修改特定字段直接用.save(),updatedProject会完全覆盖savedProject，没有修改的字段会被覆盖成默认值或者为空

    /**
     * Delete the project
     *
     * @param projectId
     * @return a success message string
     */
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable String projectId){
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("Deleted Successfully!");
    }
}

