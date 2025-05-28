package com.dtyao.crowdfunding.repository;

import java.util.List;
import java.util.Optional;

import com.dtyao.crowdfunding.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
    //继承MongoRepository，可以实现save(), findById(), findAll(), deleteById()

    //Read
    List<Project> findByUserId(String userId);
    //List<Project> findByTitle(String title);
    Optional<Project> findByProjectId(String projectId);

    //Delete
    //To be extended: deleteByProjectIDAndUserId
    void deleteByProjectId(String projectId);

}
