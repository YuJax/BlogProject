package com.example.blog.service;

public class Message {
    private String message;
    private String token;

    public Message() {
        this.message = "";
        this.token = null;
    }

    public Message(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
