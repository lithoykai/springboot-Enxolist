package com.enxolist.enxolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enxolist.enxolist.model.Request.TaskRequest;
import com.enxolist.enxolist.persistence.entity.Task;
import com.enxolist.enxolist.persistence.repositories.ITaskRepository;
import com.enxolist.enxolist.services.TaskService;
import com.enxolist.enxolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tasks/")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private ITaskRepository repository;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody TaskRequest request, HttpServletRequest httpRequest) {
        var idUser = httpRequest.getAttribute("idUser");
        request.setIdUser((String) idUser);
        return ResponseEntity.ok(service.create(request, httpRequest));
    }
    
    @GetMapping("/")    
    public List<Task> list(HttpServletRequest request){ 
       var idUser = request.getAttribute("idUser");
       return this.service.listForID((String) idUser);

    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Task taskModel, HttpServletRequest request, @PathVariable String id){
        var idUser = request.getAttribute("idUser");
        var task = this.repository.findById(id).orElse(null);
        if(task == null){ 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found.");
        }

        if(!task.getIdUser().equals(idUser)){ 
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User without permission to change this task.");
        }

        Utils.copyNonNullProperties(taskModel,task);

        return ResponseEntity.ok(this.repository.save(task));
    }
}
