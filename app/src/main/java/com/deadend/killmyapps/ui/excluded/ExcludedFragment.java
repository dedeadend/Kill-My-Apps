package com.deadend.killmyapps.ui.excluded;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.deadend.killmyapps.databinding.FragmentExcludedBinding;

public class ExcludedFragment extends Fragment {

    private FragmentExcludedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExcludedViewModel excludedViewModel =
                new ViewModelProvider(this).get(ExcludedViewModel.class);

        binding = FragmentExcludedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textDashboard;
        //excludedViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}