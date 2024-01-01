package com.enxolist.enxolist.model.Request;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
public class TaskRequest {
    
    
    private String title;   
    private String description;   
    private LocalDateTime endAt;
    private LocalDateTime startAt;
    private String priority;

    @CreatedDate
    private LocalDateTime createdAt;
    private String idUser;
}
