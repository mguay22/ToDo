package com.mguay.android.todo.data;


import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ToDoRepository {
    private static ToDoRepository sToDoRepository;
    private ToDoDao toDoDao;
    private LiveData<List<ToDo>> toDos;

    private ToDoRepository(ToDoDao toDoDao) {
        this.toDoDao = toDoDao;
        toDos = toDoDao.getAll();
    }

    public static ToDoRepository get(ToDoDao toDoDao) {
        if (sToDoRepository == null) {
            sToDoRepository = new ToDoRepository(toDoDao);
        }
        return sToDoRepository;
    }

    public LiveData<List<ToDo>> getTodos() {
        return toDos;
    }

    public void insertTodo(ToDo toDo) {
        new insertAsyncTask(toDoDao).execute(toDo);
    }

    private static class insertAsyncTask extends AsyncTask<ToDo, Void, Void> {

        private ToDoDao mAsyncTaskDao;

        insertAsyncTask(ToDoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ToDo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void updateToDo(ToDo toDo) {
        new updateAsyncTask(toDoDao).execute(toDo);
    }

    private static class updateAsyncTask extends AsyncTask<ToDo, Void, Void> {

        private ToDoDao mAsyncTaskDao;

        updateAsyncTask(ToDoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ToDo... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    public void deleteTodo(ToDo toDo) {
        new deleteAsyncTask(toDoDao).execute(toDo);
    }

    private static class deleteAsyncTask extends AsyncTask<ToDo, Void, Void> {

        private ToDoDao mAsyncTaskDao;

        deleteAsyncTask(ToDoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ToDo... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
