package com.deadend.killmyapps.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deadend.killmyapps.App;
import com.deadend.killmyapps.R;
import com.deadend.killmyapps.model.AppInfo;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    List<AppInfo> appList;
    onPauseIconClickListener listener;

    public HomeRecyclerViewAdapter(List<AppInfo> appList, onPauseIconClickListener listener) {
        this.appList = appList;
        this.listener = listener;
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
        holder.pause.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    @Override
    public void onClick(View v) {
        String pkgName = (String) v.getTag();
        for (int i=0; i<appList.size(); i++){
            if(appList.get(i).getPkgName().equals(pkgName)){
                appList.remove(i);
                notifyItemRemoved(i);
                listener.onPauseIconClick(i);
                break;
            }
        }
    }

    public interface onPauseIconClickListener{
        void onPauseIconClick(int position);
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
