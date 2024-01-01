package com.enxolist.enxolist.persistence.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.enxolist.enxolist.model.Request.TaskRequest;

import lombok.Data;

@Data
@Document
public class Task {

    @Id
    private String id;
    
    private String title;   
    private LocalDateTime endAt;
    private String description;  
    private LocalDateTime startAt;
    private String priority;

    @CreatedDate
    private LocalDateTime createdAt;
    private String idUser;

    public Task() {};

    public Task(TaskRequest request){ 
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.priority = request.getPriority();
        this.createdAt = request.getCreatedAt();
        this.startAt = request.getStartAt();
        this.endAt = request.getEndAt();
        this.idUser = request.getIdUser();
    }
}
