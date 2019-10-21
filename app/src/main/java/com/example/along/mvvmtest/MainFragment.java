package com.example.along.mvvmtest;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.along.mvvmtest.utils.AppLog;

public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MainFragment";

    private EditText searchEdit;           //搜索输入框

    private Button searchBtn;              //搜索确认框

    private Button enterInfoBtn;            //进入新增信息页面

    private TextView contentText;           //当列表为空或搜索为空时，显示该控件

    private RecyclerView infoList;          //信息列表

    private InfoViewModel infoViewModel;

    private Context context;

    private InfoAdapter adapter;

    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        adapter = new InfoAdapter(this);
        infoList.setLayoutManager(new LinearLayoutManager(context));
        infoList.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取 InfoViewModel 的实例
        infoViewModel = ViewModelProviders.of(mainActivity).get(InfoViewModel.class);
        //观察列表的数据，如果表中有数据或者数据更新了，就同步刷新列表界面
        infoViewModel.getInfoList().observe(this, infos -> {
            AppLog.debug(TAG, "findInfos() observe");
            if (infos == null || infos.isEmpty()) {
                AppLog.debug(TAG, "infos == null or infos is empty");
                infoList.setVisibility(View.GONE);
                contentText.setVisibility(View.VISIBLE);
            } else {
                infoList.setVisibility(View.VISIBLE);
                contentText.setVisibility(View.GONE);
                adapter.updateInfoList(infos);
            }
        });
    }

    @Override
    public void onResume() {
        AppLog.debug(TAG, "onResume()");
        super.onResume();
        registerListener();
    }

    @Override
    public void onPause() {
        AppLog.debug(TAG, "onPause()");
        unregisterListener();
        super.onPause();
    }

    //删除数据
    public void deleteInfos(Info... infos) {
        infoViewModel.deleteInfos(infos);
    }

    //更新数据
    public void updateInfo(Info info) {
        //这里是为了演示 viewmodel 可以用于 fragment 之间的通信
        infoViewModel.setUpdateFlag(true);
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.updateInfo(info);
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout, infoFragment)
                .addToBackStack(null)
                .commit();
    }

    //注册点击事件
    private void registerListener() {
        enterInfoBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    //反注册点击事件
    private void unregisterListener() {
        enterInfoBtn.setOnClickListener(null);
        searchBtn.setOnClickListener(null);
    }

    private void initView(View view) {
        searchEdit = view.findViewById(R.id.search_et);
        searchBtn = view.findViewById(R.id.search_btn);
        enterInfoBtn = view.findViewById(R.id.enter_info_btn);
        contentText = view.findViewById(R.id.content_tv);
        infoList = view.findViewById(R.id.info_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter_info_btn:
                infoViewModel.setUpdateFlag(false);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_layout, new InfoFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.search_btn:
                infoViewModel.findInfosByName(searchEdit.getText().toString());
                break;
            default:
                /* Do nothing. */
                break;
        }
    }
}
