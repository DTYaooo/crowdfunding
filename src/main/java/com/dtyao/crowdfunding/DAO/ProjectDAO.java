package com.dtyao.crowdfunding.DAO;

import java.util.List;
import java.util.Optional;

import com.dtyao.crowdfunding.model.Project;
//import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectDAO {
    //继承MongoRepository，可以实现save(), findById(), findAll(), deleteById()

    void save(Project project);
    List<Project> findByUserId(String userId);
    //List<Project> findByTitle(String title);
    Optional<Project> findByProjectId(String projectId);
    void deleteByProjectId(String projectId);
    //To be extended: deleteByProjectIDAndUserId

}
