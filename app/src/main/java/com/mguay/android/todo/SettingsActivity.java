package com.mguay.android.todo;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class SettingsActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, SettingsActivity.class);
    }

    @Override
    protected Fragment createFragment() { return new SettingsFragment(); }
}
