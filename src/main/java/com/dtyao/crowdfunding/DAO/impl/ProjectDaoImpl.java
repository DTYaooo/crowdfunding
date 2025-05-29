package com.dtyao.crowdfunding.DAO.impl;

import com.dtyao.crowdfunding.DAO.ProjectDAO;
import com.dtyao.crowdfunding.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate; //Spring提供的Mongodb的核心操作类
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.Query;
//这个 Query 类没有带参数的构造函数
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProjectDaoImpl implements ProjectDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Project project){
        mongoTemplate.save(project);
    }

    @Override
    public List<Project> findByUserId(String userId){ //普通字段，多条记录
        return mongoTemplate.find(
                new Query(Criteria.where("userId").is(userId)),  // 查询条件：userId 等于传入的参数
                Project.class                                     // 查出来的数据映射成 Project 对象
        );
    }
    /*
    Criteria.where("userId")表示：我们要查的字段叫 "userId"
    .is(userId)表示：该字段的值要等于传入的 userId
     */

    @Override
    public Optional<Project> findByProjectId(String projectId){ //主键，精确查一条
        return Optional.ofNullable( //把可能为null的结果包成Optional，避免空指针，如果查到了返回Optional.of(project)，否则Optional.empty()
                mongoTemplate.findById(projectId, Project.class) //根据ProjectId字段查找记录，返回Project类型的对象
        );
    }

    @Override
    public void deleteByProjectId(String projectId){
        Project query = new Project(); // 创建一个 Project 实例
        query.setProjectId(projectId); // 设置 projectId 字段，用作查询条件
        mongoTemplate.remove(query);  //根据对象进行删除
    }

}
