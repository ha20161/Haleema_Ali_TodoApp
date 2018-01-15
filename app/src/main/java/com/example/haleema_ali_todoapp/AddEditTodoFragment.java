package com.example.haleema_ali_todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by Haleema on 25/12/2017.
 */

public class AddEditTodoFragment extends Fragment {

    private static final String ARG_TODO_ID = "todo_id";

    private Todo mTodo;
    private EditText mEditTextTitle;
    private EditText mEditTextDetail;
    private Button mButtonDate;
    private CheckBox mCheckBox;

    public static AddEditTodoFragment newInstance(UUID todoId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TODO_ID, todoId);

        AddEditTodoFragment fragment = new AddEditTodoFragment();
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

        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        mEditTextTitle = (EditText) view.findViewById(R.id.todo_title);
        mEditTextTitle.setText(mTodo.getTitle());
        mEditTextTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // This line is intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mTodo.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable e) {

                // This line is intentionally left blank
            }
        });

        mEditTextDetail = (EditText) view.findViewById(R.id.todo_detail);
        mEditTextDetail.setText(mTodo.getDetail());
        mEditTextDetail.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

                // This line is intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int i1, int i2) {

                mTodo.setDetail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable e) {

                // This line is intentionally left blank
            }
        });

        mButtonDate = (Button) view.findViewById(R.id.todo_date);
        String outputPattern = "EEE dd-MMM-yyyy h:mm a";
        SimpleDateFormat outputDate = new SimpleDateFormat(outputPattern);
        mButtonDate.setText(outputDate.format(mTodo.getDate()).toString());

        mButtonDate.setEnabled(false);

        mCheckBox = (CheckBox) view.findViewById(R.id.todo_complete);

        mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mTodo.setComplete(isChecked);
            }

        });

        if(mTodo.isComplete()){
            mCheckBox.setChecked(true);
        }

        FloatingActionButton saveTodo;
        saveTodo = (FloatingActionButton) view.findViewById(R.id.saveTodo);
        saveTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
                NavUtils.navigateUpFromSameTask(getActivity());
            }
        });

        return view;
    }
}