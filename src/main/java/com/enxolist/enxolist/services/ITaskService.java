package com.enxolist.enxolist.services;

import java.util.List;

import com.enxolist.enxolist.model.Request.TaskRequest;
import com.enxolist.enxolist.model.Response.TaskResponse;
import com.enxolist.enxolist.persistence.entity.Task;

import jakarta.servlet.http.HttpServletRequest;

public interface ITaskService {

    TaskResponse create(TaskRequest request, HttpServletRequest httpRequest);

    List<Task> listForID(String idUser);

    TaskResponse update(Task taskModel, String id, String idUser);
}
