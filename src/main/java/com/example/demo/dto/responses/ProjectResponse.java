package com.example.demo.dto.responses;

import com.example.demo.models.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectResponse {
    private Long id;
    private String name;
    private Map<Long, TaskMiniInfoResponse> tasks = new HashMap<>();
    private Map<Long, TaskStatus> columns;
    private List<Long> columnOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<TaskMiniInfoResponse> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(List<TaskMiniInfoResponse> tasks) {
//        this.tasks = tasks;
//    }

    public Map<Long, TaskMiniInfoResponse> getTasks() {
        return tasks;
    }

    public void setTasks(Map<Long, TaskMiniInfoResponse> tasks) {
        this.tasks = tasks;
    }


//    public List<TaskStatus> getColumns() {
//        return columns;
//    }
//
//    public void setColumns(List<TaskStatus> columns) {
//        this.columns = columns;
//    }


    public Map<Long, TaskStatus> getColumns() {
        return columns;
    }

    public void setColumns(Map<Long, TaskStatus> columns) {
        this.columns = columns;
    }

    public List<Long> getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(List<Long> columnOrder) {
        this.columnOrder = columnOrder;
    }
}
