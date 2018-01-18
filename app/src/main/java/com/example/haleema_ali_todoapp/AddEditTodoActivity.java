package com.example.haleema_ali_todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

/**
 * Created by Haleema on 25/12/2017.
 */

public class AddEditTodoActivity extends AppCompatActivity {

    private static final String EXTRA_TODO_ID = "todo_id";

    //Any calling activity would call this static method and pass the necessary
    //key, value data pair in an intent object
    public static Intent newIntent(Context packageContext, UUID todoId){

        Intent intent = new Intent(packageContext, AddEditTodoActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }

    /*
        To decouple the fragment and make it reusable, the TodoFragment has a newInstance method
        that receives a todoId and returns the fragment
    */
    protected Fragment createFragment(){
        UUID todoId = (UUID) getIntent().getSerializableExtra(EXTRA_TODO_ID);
        return AddEditTodoFragment.newInstance(todoId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){

            Fragment todoFragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, todoFragment).commit();
        }
    }
}