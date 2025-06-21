package com.deadend.killmyapps.ui.excluded;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.deadend.killmyapps.R;
import com.deadend.killmyapps.databinding.FragmentExcludedBinding;
import com.deadend.killmyapps.model.AppInfo;

import java.util.List;

public class ExcludedFragment extends Fragment implements ExcludedRecyclerViewAdapter.onIconClickListener {

    private FragmentExcludedBinding binding;
    ExcludedViewModel excludedViewModel;

    private ExcludedRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        excludedViewModel = new ViewModelProvider(this).get(ExcludedViewModel.class);
        binding = FragmentExcludedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.excludedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_scale_in);
        binding.excludedRecyclerView.setLayoutAnimation(animation);
        setObservers();
        setListeners();
        excludedViewModel.refreshList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        excludedViewModel.getExcludedList().removeObservers(getViewLifecycleOwner());
        binding = null;
    }

    private void setObservers() {
        excludedViewModel.getExcludedList().observe(getViewLifecycleOwner(), new Observer<List<AppInfo>>() {
            @Override
            public void onChanged(List<AppInfo> appInfos) {
                setAdapter();
                if (excludedViewModel.getAppsList().getValue().isEmpty() && appInfos.isEmpty()) {
                    binding.search.setVisibility(View.GONE);
                } else {
                    binding.search.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setListeners() {
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                excludedViewModel.refreshList();
                binding.refreshLayout.setRefreshing(false);
            }
        });
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null)
                    adapter.filterList(newText);
                return true;
            }
        });
    }

    private void setAdapter() {
        adapter = new ExcludedRecyclerViewAdapter(excludedViewModel.getAppsList().getValue(),
                excludedViewModel.getExcludedList().getValue(), this);
        binding.excludedRecyclerView.swapAdapter(adapter, true);
    }

    @Override
    public void onAddIconClick(AppInfo appInfo) {
        excludedViewModel.addExcluded(appInfo);
        Toast.makeText(getContext(), "'" + appInfo.getName() + "' added to excluded list", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveIconClick(AppInfo appInfo) {
        excludedViewModel.removeExcluded(appInfo);
        Toast.makeText(getContext(), "'" + appInfo.getName() + "' removed from excluded list", Toast.LENGTH_SHORT).show();
    }
}