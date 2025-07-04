package com.deadend.killmyapps;

import com.deadend.killmyapps.model.AppInfo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class SuUtils {
    public static boolean checkSU() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    public static boolean killApp(String pkgName) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("am force-stop " + pkgName + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    public static boolean killListOfApps(List<AppInfo> appList) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            boolean killMyApps = false;
            for (AppInfo app : appList) {
                if (app.getPkgName().equals("com.deadend.killmyapps")) {
                    killMyApps = true;
                    continue;
                }
                os.writeBytes("am force-stop " + app.getPkgName() + "\n");
            }
            os.flush();
            App.toast(appList.size() + " apps killed successfully!");
            if (killMyApps)
                os.writeBytes("am force-stop com.deadend.killmyapps\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }
}
