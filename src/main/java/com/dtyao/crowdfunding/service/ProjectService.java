package com.dtyao.crowdfunding.service;
import com.dtyao.crowdfunding.model.DTO.ProjectRequestDTO;
import com.dtyao.crowdfunding.model.DTO.ProjectResponseDTO;
import com.dtyao.crowdfunding.model.DTO.ProjectUpdateDTO;
import com.dtyao.crowdfunding.DAO.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dtyao.crowdfunding.model.Project;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    /**
     * Submits a crowdfunding project.
     *
     * @param requestDTO
     * @return the info of submitted project and a success message
     */
    public ProjectResponseDTO submitProject(ProjectRequestDTO requestDTO){
        //build project
        Project project = new Project();
        project.setProjectId(UUID.randomUUID().toString());
        project.setTitle(requestDTO.getTitle());
        project.setDescription(requestDTO.getDescription());
        project.setCategory(requestDTO.getCategory());
        project.setGoalAmount(requestDTO.getGoalAmount());
        project.setDurationDays(requestDTO.getDurationDays());
        project.setCreatedAt(LocalDate.now());
        project.setUpdatedAt(LocalDate.now());
        project.setDeadline(calculateDdl(project.getCreatedAt(), project.getDurationDays()));
        project.setUserId("testuser");
        project.setStatus("submitted");

        //store project in the mongodb
        projectDAO.save(project);

        //return the result of response
        ProjectResponseDTO responseDTO = new ProjectResponseDTO();
        responseDTO.setProjectId(project.getProjectId());
        responseDTO.setTitle(project.getTitle());
        responseDTO.setStatus(project.getStatus());
        responseDTO.setMessage("Submitted Successfully!");

        return responseDTO;
    }

    /**
     * Search the list of projects according to userId
     *
     * @param userId
     * @return list of projects submitted by the user
     */
    public List<ProjectResponseDTO> getProjectsByUserId(String userId){
        List<Project> project = projectDAO.findByUserId(userId);
        return project.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
        /*
        stream():转换stream流对象，对元素进行链式处理，不需要for循环
        map():映射，对stream中每个元素执行一个函数
        this::method调用本类中的方法
        等价于Lambda表达式 project->this.method(project)
        调用mapToResponseDTO，将每个对象都转换成ProjectResponseDTO类型
        collect(Collectors.toList()):stream返回的是流不是List，由流转换成List进行返回

        List<Project> → Stream<Project> → map(Project → DTO) → Stream<DTO> → List<DTO>
         */

    }

    /**
     * Convert projects to ProjectResponseDTO
     *
     * @param project
     * @return
     */
    private ProjectResponseDTO mapToResponseDTO(Project project){
        ProjectResponseDTO responseDTO = new ProjectResponseDTO();
        responseDTO.setProjectId(project.getProjectId());
        responseDTO.setTitle(project.getTitle());
        responseDTO.setStatus(project.getStatus());
        responseDTO.setMessage(null);
        return responseDTO;
    }

    /**
     * Update the project
     *
     * @param projectId
     * @param updateDTO
     * @return a success message string
     */
    public ProjectResponseDTO updateProject(String projectId, ProjectUpdateDTO updateDTO){
        Project savedProject = projectDAO.findByProjectId(projectId).orElseThrow();
        savedProject.setTitle(updateDTO.getTitle());
        savedProject.setDescription(updateDTO.getDescription());
        savedProject.setCategory(updateDTO.getCategory());
        savedProject.setGoalAmount(updateDTO.getGoalAmount());
        savedProject.setDurationDays(updateDTO.getDurationDays());
        savedProject.setUpdatedAt(LocalDate.now());
        savedProject.setDeadline(calculateDdl(savedProject.getCreatedAt(), updateDTO.getDurationDays()));
        projectDAO.save(savedProject);

        ProjectResponseDTO responseDTO = new ProjectResponseDTO();
        responseDTO.setProjectId(savedProject.getProjectId());
        responseDTO.setStatus(savedProject.getStatus());
        responseDTO.setTitle(savedProject.getTitle());
        responseDTO.setMessage("Updated Successfully!");
        return responseDTO;
    }

    /**
     * Delete the project
     *
     * @param projectId
     */
    public void deleteProject(String projectId){
        projectDAO.deleteByProjectId(projectId);
    }

    /**
     * Calculate deadline
     *
     * @param creatAt date of creation
     * @param days duration days
     * @return deadline
     */
    private LocalDate calculateDdl(LocalDate creatAt, int days){
        LocalDate ddl = creatAt.plusDays(days);
        return ddl;
    }
}
