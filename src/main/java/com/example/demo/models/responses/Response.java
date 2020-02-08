package com.example.demo.models.responses;


import java.util.ArrayList;
import java.util.List;

public class Response {

    private int resultCode;
    private List<String> messages;
    private Object body;

    public Response(int resultCode, List<String> messages) {
        this.resultCode = resultCode;
        this.messages = messages;
        this.body = null;
    }

    public Response(int resultCode, Object body) {
        this.resultCode = resultCode;
        this.messages = new ArrayList<>();
        this.body = body;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
