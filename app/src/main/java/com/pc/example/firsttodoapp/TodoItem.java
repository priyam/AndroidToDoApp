package com.pc.example.firsttodoapp;

/**
 * Created by priyam on 1/17/15.
 */
public class TodoItem {

    private long id;
    private String body;
    private int priority;

    public TodoItem(String body, int priority) {
        super();
        this.body = body;
        this.priority = priority;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return body;
    }
}