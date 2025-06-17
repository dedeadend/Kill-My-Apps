package com.deadend.killmyapps.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.deadend.killmyapps.R;
import com.deadend.killmyapps.databinding.FragmentHomeBinding;
import com.deadend.killmyapps.model.AppInfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment implements HomeRecyclerViewAdapter.onPauseIconClickListener {

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;
    private HomeRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_scale_in);
        binding.homeRecyclerView.setLayoutAnimation(animation);
        setObservers();
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.refreshLayout.setRefreshing(true);
        homeViewModel.refreshList();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setObservers(){
        homeViewModel.getAppsList().observe(getViewLifecycleOwner(), new Observer<List<AppInfo>>() {
            @Override
            public void onChanged(List<AppInfo> appInfos) {
                setAdapter();
                if (appInfos.size() == 0) {
                    binding.killAllBtn.setVisibility(View.GONE);
                    binding.search.setVisibility(View.GONE);
                }
                else {
                    binding.killAllBtn.setVisibility(View.VISIBLE);
                    binding.search.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setListeners(){
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homeViewModel.refreshList();
                binding.refreshLayout.setRefreshing(false);
            }
        });
        binding.killAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), homeViewModel.clearList() + " Apps Killed successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(){
        adapter = new HomeRecyclerViewAdapter(homeViewModel.getAppsList().getValue(), this);
        binding.homeRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onPauseIconClick(int position) {
        Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}