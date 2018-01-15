package com.example.haleema_ali_todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton newTodo = (FloatingActionButton) getActivity().findViewById(R.id.addTodo);
        newTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add new Todo", Toast.LENGTH_SHORT).show();
                Todo todo = new Todo();
                TodoModel.get(getActivity()).addTodo(todo);
                //Starts new instance of TodoActivity by passing intent to startActivity()
                //Intent intent = TodoActivity.newIntent(getActivity(), todo.getId());

                Intent intent = AddEditTodoActivity.newIntent(getActivity(), todo.getId());
                startActivity(intent);
            }
        });

        FloatingActionButton deleteAll = (FloatingActionButton) getActivity().findViewById(R.id.clearList);
        deleteAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                if (TodoModel.get(getActivity()).count() == 0){

                    builder.setMessage(R.string.dialog_warning).setTitle(R.string.dialog_title).setCancelable(false);

                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.cancel();
                        }
                    });

                }
                else{
                    builder.setMessage(R.string.delete_message).setTitle(R.string.dialog_title).setCancelable(false);

                    builder.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            TodoModel.get(getActivity()).todoDeleteAll();
                            Toast.makeText(getActivity(), "All Todos deleted!", Toast.LENGTH_SHORT).show();
                            updateUI();
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

        switch (item.getItemId()){

            case R.id.check_all:

                if (TodoModel.get(getActivity()).count() == 0){
                    Toast.makeText(getActivity(), "First add some Todos!", Toast.LENGTH_SHORT).show();
                }
                else {
                    TodoModel.get(getActivity()).markAllComplete();
                    Toast.makeText(getActivity(), "All Todos completed!", Toast.LENGTH_SHORT).show();
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

        //This method Starts new instance of TodoActivity by passing intent to startActivity()
        //when an existing todo is clicked
        //TodoActivity shows details stored for todo stored at mTodo index
        @Override
        public void onClick(View view) {
            // have a Toast for now
            Toast.makeText(getActivity(), mTodo.getTitle() + " clicked", Toast.LENGTH_SHORT).show();

            //   Intent intent = TodoActivity.newIntent(getActivity(), mTodo.getId());
          //  Intent intent = AddEditTodoActivity.newIntent(getActivity(), mTodo.getId());
            Intent intent = TodoDetailActivity.newIntent(getActivity(), mTodo.getId());
            startActivity(intent);
        }

        public void bind(Todo todo) {
            mTodo = todo;
            mTextViewTitle.setText(mTodo.getTitle());

            String outputPattern = "EEE dd-MMM-yyyy h:mm a";
            SimpleDateFormat outputDate = new SimpleDateFormat(outputPattern);
            mTextViewDate.setText(outputDate.format(mTodo.getDate()).toString());

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mTodo.setComplete(isChecked);

                }

            });

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