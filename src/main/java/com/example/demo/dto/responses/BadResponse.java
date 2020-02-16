package com.example.demo.dto.responses;

import java.util.List;

public class BadResponse extends Response {
    public BadResponse(List<String> messages) {
        super(1, messages);
    }
}
