package com.mguay.android.todo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class ToDoListFragment extends Fragment {

    private RecyclerView mToDoRecyclerView;
    private ToDoAdapter mAdapter;
    private static final int REQUEST_TO_DO_CODE = 0;

    public static ToDoListFragment newInstance() {
        return new ToDoListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_do_list_fragment, container, false);

        mToDoRecyclerView = view.findViewById(R.id.to_do_recycler_view);
        mToDoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateUI(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_TO_DO_CODE) {
            if (data == null) {
                return;
            }

            int position = data.getIntExtra(ToDoActivity.EXTRA_POSITION, 0);
            updateUI(position);
        }
    }

    private void updateUI(int position) {
        ToDoList toDoList = ToDoList.get(getActivity());
        List<ToDo> toDos = toDoList.getToDos();

        if (mAdapter == null) {
            mAdapter = new ToDoAdapter(toDos);
            mToDoRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemChanged(position);
        }
    }

    private class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoHolder> {

        public class ToDoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mTitleTextView;
            private TextView mDueDateTextView;
            private TextView mPriorityTextView;
            private CheckBox mIsCompleteCheckBox;
            private ToDo mToDo;
            private int mPosition;

            public ToDoHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.list_item_to_do, parent, false));
                mTitleTextView = itemView.findViewById(R.id.to_do_title);
                mDueDateTextView = itemView.findViewById(R.id.to_do_due_date);
                mPriorityTextView = itemView.findViewById(R.id.to_do_priority);
                mIsCompleteCheckBox = itemView.findViewById(R.id.is_complete_check_box);
                itemView.setOnClickListener(this);

                mIsCompleteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        mToDo.setComplete(b);
                    }
                });
            }

            private void bind(ToDo toDo, int position) {
                mToDo = toDo;
                mPosition = position;
                mTitleTextView.setText(mToDo.getTitle());
                String formattedDate = new SimpleDateFormat("E, MMM d, y", Locale.ENGLISH)
                        .format(toDo.getDueDate());
                mDueDateTextView.setText(formattedDate);
                mPriorityTextView.setText(toDo.getPriority().name());
                mIsCompleteCheckBox.setChecked(toDo.isComplete());
            }

            @Override
            public void onClick(View view) {
                Intent intent = ToDoActivity.newIntent(
                        view.getContext(),
                        mToDo.getToDoId(),
                        mPosition
                );
                startActivityForResult(intent, REQUEST_TO_DO_CODE);
            }
        }

        private List<ToDo> mToDos;

        public ToDoAdapter(List<ToDo> toDos) {
            mToDos = toDos;
        }

        @Override
        public ToDoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ToDoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ToDoHolder holder, int position) {
            ToDo toDo = mToDos.get(position);
            holder.bind(toDo, position);
        }

        @Override
        public int getItemCount() {
            return mToDos.size();
        }
    }

}
