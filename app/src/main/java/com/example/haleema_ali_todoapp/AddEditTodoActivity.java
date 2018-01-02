package com.example.haleema_ali_todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Haleema on 25/12/2017.
 */

public class AddEditTodoActivity extends AppCompatActivity {

    private static final String EXTRA_TODO_ID = "todo_id";

    private ViewPager mViewPager;
    private List<Todo> mTodos;

    public static Intent newIntent(Context packageContext, UUID todoId){

        Intent intent = new Intent(packageContext, AddEditTodoActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }

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