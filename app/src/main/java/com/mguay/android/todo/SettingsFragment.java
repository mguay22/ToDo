package com.mguay.android.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private Button themeOne;
    private Button themeTwo;

    public static final String THEME_EXTRA = "theme_extra";
    public static final int THEME_RESULT = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        themeOne = view.findViewById(R.id.theme_one);
        themeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(THEME_EXTRA, 0);
                getActivity().setResult(THEME_RESULT, intent);
                getActivity().finish();
            }
        });

        themeTwo = view.findViewById(R.id.theme_two);
        themeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(THEME_EXTRA, 1);
                getActivity().setResult(THEME_RESULT, intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
