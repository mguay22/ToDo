package com.mguay.android.todo;

import androidx.fragment.app.Fragment;

public class ToDoListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return ToDoListFragment.newInstance(); }
}
