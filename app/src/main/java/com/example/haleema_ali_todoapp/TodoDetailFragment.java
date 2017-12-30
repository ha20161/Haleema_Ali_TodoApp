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

import java.util.UUID;

/**
 * Created by Haleema on 28/12/2017.
 */

public class TodoDetailFragment extends Fragment {

    private static final String ARG_TODO_ID = "todo_id";

    private Todo mTodo;
    private TextView mTextViewTitle;
    private TextView mTextViewDetail;
    private TextView mTextViewDate;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton editTodo = (FloatingActionButton) getActivity().findViewById(R.id.editTodo);
        editTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Edit Todo", Toast.LENGTH_SHORT).show();

                Intent intent = AddEditTodoActivity.newIntent(getActivity(), mTodo.getId());
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_detail, container, false);

        mTextViewTitle = (TextView) view.findViewById(R.id.todo_title);
        mTextViewDetail = (TextView) view.findViewById(R.id.todo_detail);
        mTextViewDate = (TextView) view.findViewById(R.id.todo_date);

        mTextViewTitle.setText(mTodo.getTitle());
        mTextViewDetail.setText(mTodo.getDetail());
        mTextViewDate.setText(mTodo.getDate().toString());

        FloatingActionButton editTodo;
        editTodo = (FloatingActionButton) view.findViewById(R.id.editTodo);
        editTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Edit Todo", Toast.LENGTH_SHORT).show();

                Intent intent = AddEditTodoActivity.newIntent(getActivity(), mTodo.getId());
                startActivity(intent);
            }
        });
        return view;
    }

}