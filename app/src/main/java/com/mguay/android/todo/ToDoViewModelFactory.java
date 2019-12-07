package com.mguay.android.todo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mguay.android.todo.data.ToDoRepository;

public class ToDoViewModelFactory implements ViewModelProvider.Factory {
    ToDoRepository toDoRepository;

    public ToDoViewModelFactory(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ToDoViewModel.class)) {
            return (T) new ToDoViewModel(toDoRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}