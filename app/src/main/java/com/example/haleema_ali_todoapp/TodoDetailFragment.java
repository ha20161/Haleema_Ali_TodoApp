package com.example.haleema_ali_todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

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

        FloatingActionButton editTodo;
        editTodo = (FloatingActionButton) view.findViewById(R.id.editTodo);
        editTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Edit " + mTodo.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = AddEditTodoActivity.newIntent(getActivity(), mTodo.getId());
                startActivity(intent);
            }
        });
        return view;
    }

}
