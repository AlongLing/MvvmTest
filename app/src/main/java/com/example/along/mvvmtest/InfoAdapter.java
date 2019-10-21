package com.example.along.mvvmtest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.along.mvvmtest.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

//信息列表的适配器。
public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {

    private static final String TAG = "InfoAdapter";

    private List<Info> infoList = new ArrayList<>();

    private MainFragment mainFragment;

    public InfoAdapter(MainFragment fragment) {
        mainFragment = fragment;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        AppLog.debug(TAG, "onCreateViewHolder()");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_info, viewGroup, false);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder infoViewHolder, int position) {
        AppLog.debug(TAG, "onBindViewHolder()");
        Info info = infoList.get(position);
        infoViewHolder.infoName.setText(info.getName());
        infoViewHolder.infoSex.setText(info.getSex() ? "男" : "女");
        infoViewHolder.infoAge.setText(String.valueOf(info.getAge()));

        //删除的点击事件
        infoViewHolder.infoDelete.setOnClickListener(v -> {
            mainFragment.deleteInfos(info);
        });

        //修改的点击事件
        infoViewHolder.itemView.setOnClickListener(v -> {
            mainFragment.updateInfo(info);
        });
    }

    @Override
    public int getItemCount() {
        return infoList == null ? 0 : infoList.size();
    }

    //当数据库中有数据更新时刷新列表。
    public void updateInfoList(List<Info> list) {
        AppLog.debug(TAG, "updateInfoList(): list = " + list);
        infoList = list;
        notifyDataSetChanged();
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {

        private TextView infoName;

        private TextView infoSex;

        private TextView infoAge;

        private TextView infoDelete;

        private View itemView;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            infoName = itemView.findViewById(R.id.info_name);
            infoSex = itemView.findViewById(R.id.info_sex);
            infoAge = itemView.findViewById(R.id.info_age);
            infoDelete = itemView.findViewById(R.id.info_delete);
        }
    }

}
