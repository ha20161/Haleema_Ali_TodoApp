package com.example.haleema_ali_todoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Haleema on 03/01/2018.
 */

public class TodoStatisticsFragment extends Fragment {

    private TextView mTextViewCompleted;
    private TextView mTextViewActive;
    private TextView mTextViewTotal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        mTextViewCompleted = (TextView) view.findViewById(R.id.tasks_completed);
        mTextViewActive = (TextView) view.findViewById(R.id.tasks_active);
        mTextViewTotal = (TextView) view.findViewById(R.id.tasks_total);

        mTextViewCompleted.setText("Recipes tried: " + TodoModel.get(getActivity()).countCompleted());

        mTextViewActive.setText("Recipes to try: " + (TodoModel.get(getActivity()).count() - TodoModel.get(getActivity()).countCompleted()));

        mTextViewTotal.setText("Total number of Recipes: " + TodoModel.get(getActivity()).count());

        return view;
    }
}
