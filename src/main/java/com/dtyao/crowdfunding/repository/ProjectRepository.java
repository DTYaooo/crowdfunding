package com.dtyao.crowdfunding.repository;

import java.util.List;
import com.dtyao.crowdfunding.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
    //继承MongoRepository，可以实现save(), findById(), findAll(), deleteById()
    List<Project> findByUserId(String userId);
}
