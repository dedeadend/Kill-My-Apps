package com.deadend.killmyapps.ui.home;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deadend.killmyapps.App;
import com.deadend.killmyapps.R;
import com.deadend.killmyapps.SuUtils;
import com.deadend.killmyapps.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    List<AppInfo> appList, backupList;
    onPauseIconClickListener listener;

    public HomeRecyclerViewAdapter(List<AppInfo> appList, onPauseIconClickListener listener) {
        this.appList = appList;
        this.listener = listener;
        backupList = new ArrayList<>(appList);
    }

    @NonNull
    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.deadend.killmyapps.R.layout.recycler_app, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.name.setText(appList.get(position).getName());
        holder.pkgName.setText(appList.get(position).getPkgName());
        holder.icon.setImageDrawable(appList.get(position).getIcon());
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(App.context, R.anim.scale_in));
        holder.pause.setImageDrawable(App.context.getDrawable(R.drawable.ic_pause));
        holder.pause.setTag(appList.get(position).getPkgName());
        holder.parent.setTag(appList.get(position).getPkgName());
        holder.pause.setOnClickListener(this);
        if (App.settings.getBoolean(App.CLICK_TO_APP_INFO, true))
            holder.parent.setOnClickListener(this);
        if (App.settings.getBoolean(App.LONG_CLICK_TO_COPY, true))
            holder.parent.setOnLongClickListener(this);
        if (App.settings.getBoolean(App.SHOW_PKGNAME, true))
            holder.pkgName.setVisibility(View.VISIBLE);
        else
            holder.pkgName.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    @Override
    public void onClick(View v) {
        String pkgName = (String) v.getTag();
        if (v == v.findViewById(R.id.kill_icon)) {
            for (int i = 0; i < appList.size(); i++) {
                if (appList.get(i).getPkgName().equals(pkgName)) {
                    if (SuUtils.killApp(pkgName)) {
                        listener.onPaused(i);
                        backupList.remove(appList.get(i));
                        appList.remove(i);
                        notifyItemRemoved(i);
                    } else {
                        listener.onSuError();
                    }
                    return;
                }
            }
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("package:" + pkgName.trim()));
            App.context.startActivity(intent);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        String pkgName = (String) v.getTag();
        ClipboardManager clipboardManager = (ClipboardManager) App.context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("pkgName", pkgName);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(App.context, "package name copied successfully!", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void filterList(String filter) {
        appList.clear();
        appList.addAll(backupList);
        for (int i = 0; i < appList.size(); i++) {
            if (!appList.get(i).getName().toLowerCase().contains(filter.toLowerCase())) {
                appList.remove(i);
                i--;
            }
        }
        notifyDataSetChanged();
    }

    public interface onPauseIconClickListener {
        void onPaused(int position);

        void onSuError();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon, pause;
        TextView name, pkgName;
        View parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            icon = itemView.findViewById(R.id.app_icon);
            pause = itemView.findViewById(R.id.kill_icon);
            name = itemView.findViewById(R.id.app_name);
            pkgName = itemView.findViewById(R.id.package_name);
        }
    }
}
