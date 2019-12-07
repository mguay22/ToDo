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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.mguay.android.todo.data.AppDatabase;
import com.mguay.android.todo.data.ToDo;
import com.mguay.android.todo.data.ToDoRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ToDoFragment extends Fragment {

    private static final String ARG_TO_DO_ID = "to_do_id";
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "DialogDate";

    private ToDo mToDo;
    private EditText mTitleField;
    private Button mDateButton;
    private ToDoViewModel toDoViewModel;
    private EditText mPriorityField;

    public static ToDoFragment newInstance(int toDoId) {
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

        mTitleField = view.findViewById(R.id.single_to_do_title);
        mDateButton = view.findViewById(R.id.to_do_due_date_button);
        mPriorityField = view.findViewById(R.id.single_to_do_priority);

        final int toDoId = getArguments().getInt(ARG_TO_DO_ID);
        Log.i("toDoFragment", Integer.toString(toDoId));

        toDoViewModel = ViewModelProviders.of(getActivity(), new ToDoViewModelFactory(
                ToDoRepository.get(AppDatabase.get(getActivity()).toDoDao())
        )).get(ToDoViewModel.class);

        toDoViewModel.getToDos().observe(this, new Observer<List<ToDo>>() {
            @Override
            public void onChanged(List<ToDo> toDos) {
                // If I had more time I would have used a hash map for efficiency reasons
                for (int i = 0; i < toDos.size(); i++) {
                    if (toDos.get(i).getTid() == toDoId) {
                        mToDo = toDos.get(i);
                    }
                }

                if (mToDo != null) {
                    mTitleField.setText(mToDo.getTitle());
                    mPriorityField.setText(mToDo.getPriority());
                    updateDate();

                } else {
                    mToDo = new ToDo();
                    mToDo.setTid(toDoId);
                    mToDo.setPriority(ToDo.Priority.A);
                    mToDo.setDueDate(new Date());
                }
            }
        });

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
                    case "B": mToDo.setPriority(ToDo.Priority.B);
                        break;
                    case "C": mToDo.setPriority(ToDo.Priority.C);
                        break;
                    case "D": mToDo.setPriority(ToDo.Priority.D);
                        break;
                    default: mToDo.setPriority(ToDo.Priority.A);
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
    public void onDestroy() {
        super.onDestroy();
        toDoViewModel.updateToDo(mToDo);
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
        Date date;

        if (mToDo.getDueDate() != null) {
            date = mToDo.getDueDate();
        } else {
            date = new Date();
            mToDo.setDueDate(date);
        }
        String formattedDate = new SimpleDateFormat("E, MMM d, y", Locale.ENGLISH).format(date);
        mDateButton.setText(formattedDate);
    }
}
