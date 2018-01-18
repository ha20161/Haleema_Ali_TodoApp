package com.example.haleema_ali_todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Haleema on 03/01/2018.
 */

public class TodoStatisticsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // call the super class onCreate to complete the creation of activity
        // like the view hierarchy
        super.onCreate(savedInstanceState);

        // set the user interface layout for this Activity
        // layout file is defined in the project res/layout/activity_main_fragment.xml file
        setContentView(R.layout.activity_fragment);

        //Drawer Layout to allow navigation bar to be pulled out from edge of the window
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        //adds toggle button to drawer layout
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialises navigation bar and adds navigation item listener
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // returns fragment manager for interacting with fragments associated with this fragments activity
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){

            // adds fragment to calling activities view.
            // a fragment is inserted into FrameLayout view object of TodoStatisticsActivity
            TodoStatisticsFragment todoStatisticsFragment = new TodoStatisticsFragment();
            fm.beginTransaction().add(R.id.fragment_container, todoStatisticsFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuitem) {
        // Handle navigation view item clicks here.

        switch (menuitem.getItemId()){
            case R.id.TodoListIcon:

                //returns to main activity
                NavUtils.navigateUpFromSameTask(TodoStatisticsActivity.this);
                break;
            case R.id.StatisticsIcon:
                break;
            default:
                break;
        }
        return true;
    }

}