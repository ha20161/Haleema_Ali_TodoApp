package com.example.haleema_ali_todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by Haleema on 28/12/2017.
 */

public class TodoDetailFragment extends Fragment {

    private static final String ARG_TODO_ID = "todo_id";

    private Todo mTodo;
    private TextView mTextViewTitle;
    private TextView mTextViewDetail;
    private Button mButtonDate;

    /*
        Rather than calling the constructor directly, Activity(s) should call newInstance
        and pass required parameters that the fragment needs to create its argument
     */

    public static TodoDetailFragment newInstance(UUID todoId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TODO_ID, todoId);

        TodoDetailFragment fragment = new TodoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /*
            Fragment accessing the intent from the hosting Activity as in the following code snippet
            allows for simple code that works.

            UUID todoId = (UUID) getActivity()
                    .getIntent().getSerializableExtra(TodoActivity.EXTRA_TODO_ID);

            The disadvantage: TodoFragment is no longer reusable as it is coupled to Activities who's
            intent has to contain todoId.

            Solution: store the todoId in the fragment's arguments bundle.
                See the TodoFragment newInstance(UUID todoId) method.

            Then to create a new fragment. the TodoActivity should call TodoFragment.newInstance(UUID)
            and pass in the UUID it retrieves from its extra argument.
         */

        UUID todoId = (UUID) getArguments().getSerializable(ARG_TODO_ID);
        mTodo = TodoModel.get(getActivity()).getTodo(todoId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_detail, container, false);

        mTextViewTitle = (TextView) view.findViewById(R.id.todo_title);
        mTextViewDetail = (TextView) view.findViewById(R.id.todo_detail);
        mButtonDate = (Button) view.findViewById(R.id.todo_date);

        mTextViewTitle.setText(mTodo.getTitle());
        mTextViewDetail.setText(mTodo.getDetail());

        String outputPattern = "EEE dd-MMM-yyyy h:mm a";
        SimpleDateFormat outputDate = new SimpleDateFormat(outputPattern);
        mButtonDate.setText(outputDate.format(mTodo.getDate()).toString());
        mButtonDate.setEnabled(false);

        //Creates and implements floating action button to delete current todo
        FloatingActionButton deleteTodo;
        deleteTodo = (FloatingActionButton) view.findViewById(R.id.deleteTodo);
        deleteTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TodoModel.get(getActivity()).deleteTodo(mTodo);
                Toast.makeText(getActivity(), mTodo.getTitle() + " recipe deleted!", Toast.LENGTH_SHORT).show();
                NavUtils.navigateUpFromSameTask(getActivity());

            }
        });

        //Creates and implements floating action button to edit current todo
        FloatingActionButton editTodo;
        editTodo = (FloatingActionButton) view.findViewById(R.id.editTodo);
        editTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Edit " + mTodo.getTitle() + " recipe", Toast.LENGTH_SHORT).show();

                //Starts new instance of AddEditTodoActivity by passing intent to startActivity()
                Intent intent = AddEditTodoActivity.newIntent(getActivity(), mTodo.getId());
                startActivity(intent);
            }
        });
        return view;
    }
}