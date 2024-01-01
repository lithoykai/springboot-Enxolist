package com.enxolist.enxolist.model.Response;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class TaskResponse {
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
}

