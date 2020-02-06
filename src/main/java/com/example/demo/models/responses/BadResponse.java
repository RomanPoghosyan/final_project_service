package com.example.demo.models.responses;

import java.util.List;

public class BadResponse extends Response {
    public BadResponse(List<String> messages) {
        super(1, messages);
    }
}
