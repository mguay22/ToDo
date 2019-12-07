package com.mguay.android.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import java.util.UUID;

public class ToDoActivity extends SingleFragmentActivity {

    public static final String EXTRA_TO_DO_ID =
            "com.mguay.android.todo.to_do_id";

    public static final String EXTRA_POSITION =
            "com.mguya.android.todo.to_do_position";

    public static Intent newIntent(Context packageContext, int toDoId) {
        Intent intent = new Intent(packageContext, ToDoActivity.class);
        intent.putExtra(EXTRA_TO_DO_ID, toDoId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int toDoId = getIntent().getIntExtra(EXTRA_TO_DO_ID, 0);
        setResult(Activity.RESULT_OK, getIntent());
        return ToDoFragment.newInstance(toDoId);
    }
}
