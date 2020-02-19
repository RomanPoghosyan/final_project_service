package com.example.demo.dto.requests;

import java.util.List;

public class ColumnReorderRequest {
    private Long projectId;
    private List<Long> columnOrder;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<Long> getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(List<Long> columnOrder) {
        this.columnOrder = columnOrder;
    }
}
