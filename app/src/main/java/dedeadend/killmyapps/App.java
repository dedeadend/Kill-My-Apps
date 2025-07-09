package dedeadend.killmyapps;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class App extends Application {

    public static final String THEME_MODE = "themeMode";
    public static final String LIST_MODE = "listMode";
    public static final String HIDE_KILL_MY_APPS = "hideKillMyApps";
    public static final String HIDE_DEFAULT_LAUNCHER = "hideDefaultLauncher";
    public static final String HIDE_SYSTEM_UI = "hideSystemUI";
    public static final String SHOW_PKGNAME = "showPkgName";
    public static final String CLICK_TO_APP_INFO = "clickToAppInfo";
    public static final String LONG_CLICK_TO_COPY = "longClickToCopy";

    public static Database database;
    public static SharedPreferences settings;
    public static Context context;
    public static Handler handler;
    public static boolean isFirstRun;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, Database.class, "database").build();
        settings = getSharedPreferences("settings", MODE_PRIVATE);
        context = getApplicationContext();
        handler = new Handler(Looper.getMainLooper());
        isFirstRun = settings.getBoolean("isFirstRun", true);
        if (isFirstRun)
            settings.edit().putBoolean("isFirstRun", false).apply();
    }

    public static void setAppThemeMode() {
        int themeMode = App.settings.getInt(App.THEME_MODE, 0);
        if (themeMode == 0)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        else if (themeMode == 1)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if (themeMode == 2)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    public static void toast(Activity activity, String title, String message) {
        MotionToast.Companion.createColorToast(activity, title, message,
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular));

    }
}
