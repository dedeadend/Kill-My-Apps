package com.deadend.killmyapps.ui.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.deadend.killmyapps.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObservers();
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setObservers() {
        settingsViewModel.getThemeMode().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0)
                    binding.themeSettings.check(binding.autoTheme.getId());
                else if (integer == 1)
                    binding.themeSettings.check(binding.lightTheme.getId());
                else if (integer == 2)
                    binding.themeSettings.check(binding.darkTheme.getId());
            }
        });
        settingsViewModel.getListMode().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0)
                    binding.modeSettings.check(binding.userMode.getId());
                else if (integer == 1)
                    binding.modeSettings.check(binding.launcherMode.getId());
                else if (integer == 2)
                    binding.modeSettings.check(binding.systemMode.getId());
                binding.hideSystemUi.setEnabled(integer == 2);
            }
        });
        settingsViewModel.getHideKillMyApps().observe(getViewLifecycleOwner(), binding.hideKillMyApps::setChecked);
        settingsViewModel.getHideDefaultLauncher().observe(getViewLifecycleOwner(), binding.hideDefaultLauncher::setChecked);
        settingsViewModel.getHideSystemUI().observe(getViewLifecycleOwner(), binding.hideSystemUi::setChecked);
        settingsViewModel.getShowAppsPkgName().observe(getViewLifecycleOwner(), binding.showPkgname::setChecked);
        settingsViewModel.getClickToAppInfo().observe(getViewLifecycleOwner(), binding.clickToAppInfo::setChecked);
        settingsViewModel.getLongClickToCopy().observe(getViewLifecycleOwner(), binding.longClickToCopy::setChecked);
    }

    private void setListeners() {
        binding.themeSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == binding.autoTheme.getId())
                    settingsViewModel.setThemeMode(0);
                else if (checkedId == binding.lightTheme.getId())
                    settingsViewModel.setThemeMode(1);
                else if (checkedId == binding.darkTheme.getId())
                    settingsViewModel.setThemeMode(2);
            }
        });
        binding.modeSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == binding.userMode.getId())
                    settingsViewModel.setListMode(0);
                else if (checkedId == binding.launcherMode.getId())
                    settingsViewModel.setListMode(1);
                else if (checkedId == binding.systemMode.getId())
                    settingsViewModel.setListMode(2);
            }
        });
        binding.hideKillMyApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.setHideKillMyApps(binding.hideKillMyApps.isChecked());
            }
        });
        binding.hideDefaultLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.setHideDefaultLauncher(binding.hideDefaultLauncher.isChecked());
            }
        });
        binding.hideSystemUi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.setHideSystemUI(binding.hideSystemUi.isChecked());
            }
        });
        binding.showPkgname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.setShowAppsPkgName(binding.showPkgname.isChecked());
            }
        });
        binding.clickToAppInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.setClickToAppInfo(binding.clickToAppInfo.isChecked());
            }
        });
        binding.longClickToCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.setLongClickToCopy(binding.longClickToCopy.isChecked());
            }
        });
    }
}