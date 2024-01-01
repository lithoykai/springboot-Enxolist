package com.enxolist.enxolist.persistence.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enxolist.enxolist.persistence.entity.Task;


@Repository
public interface ITaskRepository extends MongoRepository<Task, String>{
    

    List<Task> findByIdUser(String idUser);

}
