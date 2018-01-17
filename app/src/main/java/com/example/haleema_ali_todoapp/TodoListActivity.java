package com.example.haleema_ali_todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class TodoListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_fragment);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        final Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){

            TodoListFragment todoListFragment = new TodoListFragment();

            // adds fragment to calling activities view.
            // a fragment is inserted into FrameLayout view object of TodoListActivity
            fm.beginTransaction().add(R.id.fragment_container, todoListFragment).commit();
        }

        //Creates and implements floating action button to add new recipe
        FloatingActionButton newTodo = (FloatingActionButton) findViewById(R.id.addTodo);
        newTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(TodoListActivity.this, "Add new Recipe", Toast.LENGTH_SHORT).show();
                Todo todo = new Todo();
                TodoModel.get(TodoListActivity.this).addTodo(todo);

                //Starts new instance of AddEditTodoActivity by passing intent to startActivity()
                Intent intent = AddEditTodoActivity.newIntent(TodoListActivity.this, todo.getId());
                startActivity(intent);
            }
        });

        //Creates and implements floating action button to delete all recipes
        FloatingActionButton deleteAll = (FloatingActionButton) findViewById(R.id.clearList);
        deleteAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TodoListActivity.this);

                if (TodoModel.get(TodoListActivity.this).count() == 0){

                    builder.setMessage(R.string.dialog_warning).setTitle(R.string.dialog_title).setCancelable(false);

                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.cancel();
                        }
                    });

                }

                //Dialog box to confirm action
                else{
                    builder.setMessage(R.string.delete_message).setTitle(R.string.dialog_title).setCancelable(false);

                    builder.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            TodoModel.get(TodoListActivity.this).todoDeleteAll();
                            Toast.makeText(TodoListActivity.this, "All Recipes deleted!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        }
                    });

                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.cancel();
                        }
                    });
                }

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuitem) {

        // Handles navigation menu item actions

        switch (menuitem.getItemId()){
            case R.id.TodoListIcon:
                break;
            case R.id.StatisticsIcon:

                //Starts new instance of TodoStatisticsActivity Activity by passing intent to startActivity()
                Intent intent = new Intent(TodoListActivity.this, TodoStatisticsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}