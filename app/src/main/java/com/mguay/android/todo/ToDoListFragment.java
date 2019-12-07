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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mguay.android.todo.data.AppDatabase;
import com.mguay.android.todo.data.ToDo;
import com.mguay.android.todo.data.ToDoRepository;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ToDoListFragment extends Fragment {

    private RecyclerView mToDoRecyclerView;
    private ToDoViewModel toDoViewModel;
    private ToDoAdapter mAdapter;

    public static ToDoListFragment newInstance() {
        return new ToDoListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_do_list_fragment, container, false);

        mToDoRecyclerView = view.findViewById(R.id.to_do_recycler_view);
        mToDoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        toDoViewModel = ViewModelProviders.of(getActivity(), new ToDoViewModelFactory(
                ToDoRepository.get(AppDatabase.get(getActivity()).toDoDao())
        )).get(ToDoViewModel.class);

        mAdapter = new ToDoAdapter();
        mToDoRecyclerView.setAdapter(mAdapter);

        toDoViewModel.getToDos().observe(this, new Observer<List<ToDo>>() {
            @Override
            public void onChanged(@Nullable final List<ToDo> toDos) {
                if (toDos == null) return;
                mAdapter.setToDos(toDos);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_to_do_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_to_do: {
                ToDo toDo = new ToDo();
                toDo.setTid(getRandom(Integer.MIN_VALUE, Integer.MAX_VALUE));
                toDoViewModel.insertToDo(toDo);
                Intent intent = ToDoActivity.newIntent(getActivity(), toDo.getTid());
                startActivity(intent);
                return true;
            }
            case R.id.settings: {
                Intent intent = SettingsActivity.newIntent(getActivity());
                startActivity(intent);
            }
            default:
                return super.onOptionsItemSelected(item);
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
                        mToDoRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                toDoViewModel.deleteToDo(mToDo);
                                mAdapter.notifyItemRemoved(mPosition);
                                notifyItemRangeChanged(mPosition, mAdapter.getItemCount());
                            }
                        });
                    }
                });
            }

            private void bind(ToDo toDo, int position) {
                mToDo = toDo;
                mPosition = position;
                mTitleTextView.setText(mToDo.getTitle());
                if (mToDo.getDueDate() != null) {
                    String formattedDate = new SimpleDateFormat("E, MMM d, y", Locale.ENGLISH).format(mToDo.getDueDate());
                    mDueDateTextView.setText(formattedDate);
                }
                if (mToDo.getPriority() != null) {
                    mPriorityTextView.setText(mToDo.getPriority());
                }

                mIsCompleteCheckBox.setChecked(toDo.isComplete());
            }

            @Override
            public void onClick(View view) {
                Intent intent = ToDoActivity.newIntent(
                        view.getContext(),
                        mToDo.getTid()
                );
                startActivity(intent);
            }
        }

        private List<ToDo> mToDos;

        @Override
        public ToDoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ToDoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ToDoHolder holder, int position) {
            if (mToDos != null) {
                ToDo toDo = mToDos.get(position);
                holder.bind(toDo, position);
            }
        }

        public void setToDos(List<ToDo> toDos){
            mToDos = toDos;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            if (mToDos != null) {
                return mToDos.size();
            } else {
                return 0;
            }
        }
    }

    public static int getRandom(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min " + min + " greater than max " + max);
        }
        return (int) ( (long) min + Math.random() * ((long)max - min + 1));
    }

}
