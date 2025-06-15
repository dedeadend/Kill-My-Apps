package com.deadend.killmyapps.ui.home;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.deadend.killmyapps.R;
import com.deadend.killmyapps.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<ApplicationInfo> appList = getActivity().getPackageManager().getInstalledApplications(0);
        adapter = new HomeRecyclerViewAdapter(getContext(), appList);
        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_scale_in);
        binding.homeRecyclerView.setLayoutAnimation(animation);
        binding.homeRecyclerView.setAdapter(adapter);

        Toast.makeText(getContext(), String.valueOf(appList.size()), Toast.LENGTH_SHORT).show();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}