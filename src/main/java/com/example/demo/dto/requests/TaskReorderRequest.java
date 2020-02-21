package com.example.demo.dto.requests;

import java.util.List;

public class TaskReorderRequest {
    private Long columnId;
    private List<Long> taskIds;

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public List<Long> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Long> taskIds) {
        this.taskIds = taskIds;
    }
}
