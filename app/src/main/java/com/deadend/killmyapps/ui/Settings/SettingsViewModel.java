package com.deadend.killmyapps.ui.Settings;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<Integer> themeMode , listMode;
    //themeMode = 0 -> auto , 1 -> light , 2 -> dark
    //listMode = 0 -> user , 1 -> launcher , 2 -> system
    private final MutableLiveData<Boolean> hideKillMyApps, hideDefaultLauncher, hideSystemUI;

    public SettingsViewModel() {
        themeMode = new MutableLiveData<>();
        listMode = new MutableLiveData<>();
        hideKillMyApps = new MutableLiveData<>();
        hideDefaultLauncher = new MutableLiveData<>();
        hideSystemUI = new MutableLiveData<>();

    }

}