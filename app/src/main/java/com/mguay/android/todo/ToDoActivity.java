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

    public static Intent newIntent(Context packageContext, UUID toDoId, int position) {
        Intent intent = new Intent(packageContext, ToDoActivity.class);
        intent.putExtra(EXTRA_TO_DO_ID, toDoId);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID toDoId = (UUID) getIntent().getSerializableExtra(EXTRA_TO_DO_ID);
        setResult(Activity.RESULT_OK, getIntent());
        return ToDoFragment.newInstance(toDoId);
    }
}
