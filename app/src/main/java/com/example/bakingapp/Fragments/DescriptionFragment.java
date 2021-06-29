package com.example.bakingapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.R;

public class DescriptionFragment extends Fragment {
    private String description;
    public DescriptionFragment() {
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.description_text_fragment, container, false);
        TextView descriptionText = view.findViewById(R.id.instructionsTextView);
        descriptionText.setText(description);
        return view;
    }
}
