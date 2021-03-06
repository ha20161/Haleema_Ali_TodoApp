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

    /*
        Rather than calling the constructor directly, Activity(s) should call newInstance
        and pass required parameters that the fragment needs to create its argument
     */

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

        /*
            Fragment accessing the intent from the hosting Activity as in the following code snippet
            allows for simple code that works.

            UUID todoId = (UUID) getActivity()
                    .getIntent().getSerializableExtra(TodoActivity.EXTRA_TODO_ID);

            The disadvantage: AddEditTodoFragment is no longer reusable as it is coupled to Activities who's
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

        //Creates and implements floating action button to delete current todo
        FloatingActionButton deleteTodo;
        deleteTodo = (FloatingActionButton) view.findViewById(R.id.deleteTodo);
        deleteTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TodoModel.get(getActivity()).deleteTodo(mTodo);
                Toast.makeText(getActivity(), mTodo.getTitle() + " recipe deleted!", Toast.LENGTH_SHORT).show();

                //returns to main activity
                NavUtils.navigateUpFromSameTask(getActivity());

            }
        });

        //Creates and implements floating action button to save current todo
        FloatingActionButton saveTodo;
        saveTodo = (FloatingActionButton) view.findViewById(R.id.saveTodo);
        saveTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();

                //returns to main activity
                NavUtils.navigateUpFromSameTask(getActivity());
            }
        });
        return view;
    }
}