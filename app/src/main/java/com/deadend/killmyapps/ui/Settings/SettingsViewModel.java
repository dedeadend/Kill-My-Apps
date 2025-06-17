package com.deadend.killmyapps.ui.Settings;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deadend.killmyapps.App;

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
        themeMode.setValue(App.settings.getInt(App.THEME_MODE, 0));
        listMode.setValue(App.settings.getInt(App.LIST_MODE, 0));
        hideKillMyApps.setValue(App.settings.getBoolean(App.HIDE_KILL_MY_APPS, true));
        hideDefaultLauncher.setValue(App.settings.getBoolean(App.HIDE_DEFAULT_LAUNCHER, true));
        hideSystemUI.setValue(App.settings.getBoolean(App.HIDE_SYSTEM_UI, true));
    }

    public void setThemeMode(int mode){
        themeMode.setValue(mode);
        App.settings.edit().putInt(App.THEME_MODE, mode).apply();
    }
    public void setListMode(int mode){
        listMode.setValue(mode);
        App.settings.edit().putInt(App.LIST_MODE, mode).apply();
    }
    public void setHideKillMyApps(boolean hide){
        hideKillMyApps.setValue(hide);
        App.settings.edit().putBoolean(App.HIDE_KILL_MY_APPS, hide).apply();
    }
    public void setHideDefaultLauncher(boolean hide){
        hideDefaultLauncher.setValue(hide);
        App.settings.edit().putBoolean(App.HIDE_DEFAULT_LAUNCHER, hide).apply();
    }
    public void setHideSystemUI(boolean hide){
        hideSystemUI.setValue(hide);
        App.settings.edit().putBoolean(App.HIDE_SYSTEM_UI, hide).apply();
    }

    public MutableLiveData<Integer> getThemeMode() {
        return themeMode;
    }

    public MutableLiveData<Integer> getListMode() {
        return listMode;
    }

    public MutableLiveData<Boolean> getHideKillMyApps() {
        return hideKillMyApps;
    }

    public MutableLiveData<Boolean> getHideDefaultLauncher() {
        return hideDefaultLauncher;
    }

    public MutableLiveData<Boolean> getHideSystemUI() {
        return hideSystemUI;
    }
}