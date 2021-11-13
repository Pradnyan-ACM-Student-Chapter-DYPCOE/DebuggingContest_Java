package com.example.todoappjava;

public class TodoData {
    private int id;
    private String todoText;

    public TodoData(int id, String todoText) {
        this.id = id;
        this.todoText = todoText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }
}
