package com.enxolist.enxolist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enxolist.enxolist.model.Request.TaskRequest;
import com.enxolist.enxolist.model.Response.TaskResponse;
import com.enxolist.enxolist.persistence.entity.Task;
import com.enxolist.enxolist.persistence.repositories.ITaskRepository;
import com.enxolist.enxolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService implements ITaskService{

    @Autowired
    ITaskRepository repository;

    private TaskResponse creaTaskResponse(Task task) { 
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle()); 
        response.setDescription(task.getDescription());
        response.setPriority(task.getPriority());
        response.setCreatedAt(task.getCreatedAt());
        response.setStartAt(task.getStartAt());
        response.setEndAt(task.getEndAt());
        response.setIdUser(task.getIdUser());
        return response;
    }

    @Override
    public TaskResponse create(TaskRequest request, HttpServletRequest httpRequest) {
        Task task = new Task(request);
        repository.save(task);

        return creaTaskResponse(task);
        
    }

    @Override
    public List<Task> listForID(String idUser) {
        var tasks = this.repository.findByIdUser(idUser);
        return tasks;
    }

    @Override
    public TaskResponse update(Task taskModel, String id, String idUser) {
        var task = this.repository.findById(id).orElse(null);

        if(!task.getIdUser().equals(idUser)){ 
            return null;
        }

        Utils.copyNonNullProperties(taskModel,task);
        return creaTaskResponse(this.repository.save(task));
        
    }

    
    
}
