package com.example.haleema_ali_todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Haleema on 28/12/2017.
 */

public class TodoDetailActivity extends AppCompatActivity {

    private static final String EXTRA_TODO_ID = "todo_id";

    private ViewPager mViewPager;
    private List<Todo> mTodos;

    //Any calling activity would call this static method and pass the necessary
    //key, value data pair in an intent object
    public static Intent newIntent(Context packageContext, UUID todoId){

        Intent intent = new Intent(packageContext, TodoDetailActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_pager);

        final UUID todoId =(UUID) getIntent().getSerializableExtra(EXTRA_TODO_ID);

        mViewPager = findViewById(R.id.todo_view_pager);

        mTodos = TodoModel.get(this).getTodos();

        // fragment manger is created

        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {


            @Override
            public Fragment getItem(int position) {

                Todo todo = mTodos.get(position);

                //returns a new instance of TodoDetailFragment showing the details for the todo at the current index
                return TodoDetailFragment.newInstance(todo.getId());
            }

            @Override
            public int getCount() {

                return mTodos.size();
            }
        }
        );

        for (int i = 1; i < mTodos.size(); i++) {

            if(mTodos.get(i).getId().equals(todoId)) {

                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}