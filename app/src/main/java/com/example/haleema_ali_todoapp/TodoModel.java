package com.example.haleema_ali_todoapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Haleema on 25/12/2017.
 */

public class TodoModel {

    private static TodoModel sTodoModel;

    private ArrayList<Todo> mTodoList;



    public static TodoModel get(Context context) {

        if(sTodoModel == null){

            sTodoModel = new TodoModel(context);
        }

        return sTodoModel;
    }

    public TodoModel(Context context) {

        mTodoList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            Todo todo = new Todo();
            todo.setTitle("Todo title " + i);
            todo.setDetail("Detail fro task " + todo.getId().toString());
            todo.setComplete(false);

            mTodoList.add(todo);
        }
    }

    public Todo getTodo(UUID todoId) {

        for (Todo todo : mTodoList) {

            if (todo.getId().equals(todoId)){

                return todo;
            }
        }

        return null;
    }

    public ArrayList<Todo> getTodos() {

        return mTodoList;
    }

    public void addTodo(Todo todo){

        mTodoList.add(todo);
    }

    public void deleteTodo(Todo todo){

        mTodoList.remove(todo);
    }
}
