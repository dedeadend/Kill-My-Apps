package com.deadend.killmyapps.ui.home;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deadend.killmyapps.R;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<ApplicationInfo> appList;

    public HomeRecyclerViewAdapter(Context context, List<ApplicationInfo> appList) {
        this.context = context;
        this.appList = appList;
    }

    @NonNull
    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.deadend.killmyapps.R.layout.recycler_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.icon.setImageDrawable(appList.get(position).loadIcon(context.getPackageManager()));
        holder.name.setText(appList.get(position).loadLabel(context.getPackageManager()));
        holder.pkgName.setText(appList.get(position).packageName);
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_in));
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon, pause;
        TextView name, pkgName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.app_icon);
            pause = itemView.findViewById(R.id.kill_icon);
            name = itemView.findViewById(R.id.app_name);
            pkgName = itemView.findViewById(R.id.package_name);
        }
    }
}
