package com.example.demo.dto.responses;

import java.time.LocalDateTime;

public class DailyTasksResponse {
    private Long id;
    private String title;
    private LocalDateTime dueDate;

    public DailyTasksResponse(Long id, String title, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
