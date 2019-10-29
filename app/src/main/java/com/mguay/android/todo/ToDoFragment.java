package com.mguay.android.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ToDoFragment extends Fragment {

    private static final String ARG_TO_DO_ID = "to_do_id";
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "DialogDate";

    private ToDo mToDo;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mPriorityField;

    public static ToDoFragment newInstance(UUID toDoId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TO_DO_ID, toDoId);

        ToDoFragment fragment = new ToDoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.to_do_fragment, container, false);

        UUID toDoId = (UUID) getArguments().getSerializable(ARG_TO_DO_ID);
        mToDo = ToDoList.get(getActivity()).getToDo(toDoId);

        mTitleField = view.findViewById(R.id.single_to_do_title);
        mDateButton = view.findViewById(R.id.to_do_due_date_button);
        mPriorityField = view.findViewById(R.id.single_to_do_priority);

        mTitleField.setText(mToDo.getTitle());
        mPriorityField.setText(mToDo.getPriority().name());

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mToDo.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mToDo.getDueDate());
                dialog.setTargetFragment(ToDoFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mPriorityField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                switch (charSequence.toString()) {
                    case "B": mToDo.setPriority(Priority.B);
                        break;
                    case "C": mToDo.setPriority(Priority.C);
                        break;
                    case "D": mToDo.setPriority(Priority.D);
                        break;
                    default: mToDo.setPriority(Priority.A);
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mToDo.setDueDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        String formattedDate = new SimpleDateFormat("E, MMM d, y", Locale.ENGLISH).format(mToDo.getDueDate());
        mDateButton.setText(formattedDate);
    }
}
