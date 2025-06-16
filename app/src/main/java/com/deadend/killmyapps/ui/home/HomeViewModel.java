package com.deadend.killmyapps.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deadend.killmyapps.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<AppInfo>> appsList;

    public HomeViewModel() {
        appsList = new MutableLiveData<>();
        appsList.setValue(new ArrayList<>());
    }

    public void getSystemAppsList(Context context){
        List<ApplicationInfo> applications = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        for(int i=0; i<applications.size(); i++){
            if((applications.get(i).flags & ApplicationInfo.FLAG_STOPPED) == ApplicationInfo.FLAG_STOPPED) {
                applications.remove(i);
                i--;
            }
        }
        appsList.setValue(AppInfo.utils.applicationInfoList2AppInfoList(context, applications));
    }

    public void getUserAppsList(Context context){
        List<ApplicationInfo> applications = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        for (int i=0; i<applications.size(); i++){
            if (((applications.get(i).flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) ||
                    (applications.get(i).flags & ApplicationInfo.FLAG_STOPPED) == ApplicationInfo.FLAG_STOPPED){
                applications.remove(i);
                i--;
            }
        }
        appsList.setValue(AppInfo.utils.applicationInfoList2AppInfoList(context, applications));
    }

    public void getLauncherAppsList(Context context){
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> launcherApps = pm.queryIntentActivities(intent, 0);
        List<ApplicationInfo> applications = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for(int i=0; i<applications.size(); i++){
            if((applications.get(i).flags & ApplicationInfo.FLAG_STOPPED) == ApplicationInfo.FLAG_STOPPED) {
                applications.remove(i);
                i--;
            }
            else{
                boolean isLauncherApp = false;
                for(int j=0; j<launcherApps.size(); j++){
                    if(applications.get(i).packageName.equals(launcherApps.get(j).activityInfo.packageName)){
                        isLauncherApp = true;
                        launcherApps.remove(j);
                        break;
                    }
                }
                if(!isLauncherApp){
                    applications.remove(i);
                    i--;
                }
            }
        }
        appsList.setValue(AppInfo.utils.applicationInfoList2AppInfoList(context, applications));
    }
    public MutableLiveData<List<AppInfo>> getAppsList() {
        return appsList;
    }
}