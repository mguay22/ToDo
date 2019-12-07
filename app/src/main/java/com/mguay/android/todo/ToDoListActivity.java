package com.mguay.android.todo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ToDoListActivity extends SingleFragmentActivity {
    int themeChoice;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SettingsFragment.THEME_RESULT) {
            themeChoice = data.getIntExtra(SettingsFragment.THEME_EXTRA, 0);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme2);
        try {
            if (themeChoice == 0) {
                setTheme(R.style.AppTheme);
            } else if (themeChoice == 1) {
                setTheme(R.style.AppTheme2);
            }
        } catch (Exception err) {

        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() { return ToDoListFragment.newInstance(); }
}
