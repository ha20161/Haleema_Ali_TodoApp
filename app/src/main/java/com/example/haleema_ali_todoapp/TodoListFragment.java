package com.example.haleema_ali_todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haleema on 25/12/2017.
 */

public class TodoListFragment extends Fragment {

    private RecyclerView mTodoRecyclerView;
    TodoAdapter mTodoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        //initialises Recycler View
        mTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_recycler_view);

        //sets layout of recycler view
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handles menu item actions
        switch (item.getItemId()){

            case R.id.check_all:

                if (TodoModel.get(getActivity()).count() == 0){
                    Toast.makeText(getActivity(), "First add some Recipes!", Toast.LENGTH_SHORT).show();
                }
                else{
                    TodoModel.get(getActivity()).markAllComplete();
                    Toast.makeText(getActivity(), "All Recipes marked completed!", Toast.LENGTH_SHORT).show();
                    updateUI();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {

        super.onResume();
        updateUI();
    }

    private void updateUI() {

        ArrayList todos = new ArrayList<>();
        TodoModel todoModel = TodoModel.get(getContext());
        todos = todoModel.getTodos();

        if(mTodoAdapter == null){

            mTodoAdapter = new TodoAdapter(todos);
            mTodoRecyclerView.setAdapter(mTodoAdapter);
        }
        else{
            mTodoAdapter.notifyDataSetChanged();
        }
    }

    public class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Todo mTodo;
        private TextView mTextViewTitle;
        private TextView mTextViewDate;
        private CheckBox mCheckBox;

        public TodoHolder(LayoutInflater inflater, ViewGroup parent){

            super(inflater.inflate(R.layout.list_item_todo, parent, false));

            itemView.setOnClickListener(this);

            mTextViewTitle = (TextView) itemView.findViewById(R.id.todo_title);
            mTextViewDate = (TextView) itemView.findViewById(R.id.todo_date);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.todo_complete);

        }

        //This method Starts new instance of TodoDetailActivity by passing intent to startActivity()
        //when an existing todo is clicked
        //TodoActivity shows details stored for todo stored at mTodo index
        @Override
        public void onClick(View view) {

            Toast.makeText(getActivity(), mTodo.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
            Intent intent = TodoDetailActivity.newIntent(getActivity(), mTodo.getId());
            startActivity(intent);
        }

        public void bind(Todo todo) {
            mTodo = todo;
            mTextViewTitle.setText(mTodo.getTitle());

            // format for date and time
            String outputPattern = "EEE dd-MMM-yyyy h:mm a";
            SimpleDateFormat outputDate = new SimpleDateFormat(outputPattern);

            // sets format for date and time
            mTextViewDate.setText(outputDate.format(mTodo.getDate()).toString());

            //adds listener to checkbox and stores state using setComplete method from Todo class
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mTodo.setComplete(isChecked);

                }

            });

            //If todo is set as complete then shows as ticked
            if(todo.isComplete()){
                mCheckBox.setChecked(true);
            }
        }
    }

    public class TodoAdapter extends RecyclerView.Adapter<TodoListFragment.TodoHolder> {

        private List<Todo> mTodos;

        public TodoAdapter(List<Todo> todos){

            mTodos = todos;
        }

        @Override
        public TodoListFragment.TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TodoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position) {

            Todo todo = mTodos.get(position);
            holder.bind(todo);
        }

        @Override
        public int getItemCount() {

            return mTodos.size();
        }
    }
}