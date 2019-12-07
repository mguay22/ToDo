package com.mguay.android.todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mguay.android.todo.data.ToDo;
import com.mguay.android.todo.data.ToDoRepository;

import java.util.List;

public class ToDoViewModel extends ViewModel {
    private ToDoRepository toDoRepository;
    private LiveData<List<ToDo>> toDos;

    public ToDoViewModel(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
        toDos = toDoRepository.getTodos();
    }

    public LiveData<List<ToDo>> getToDos() {
        return toDos;
    }

    public void insertToDo(ToDo toDo) {
        toDoRepository.insertTodo(toDo);
    }

    public void updateToDo(ToDo toDo) {
        toDoRepository.updateToDo(toDo);
    }

    public void deleteToDo(ToDo toDo) {
        toDoRepository.deleteTodo(toDo);
    }
}
