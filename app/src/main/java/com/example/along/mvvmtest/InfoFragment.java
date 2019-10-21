package com.example.along.mvvmtest;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.along.mvvmtest.utils.AppLog;

public class InfoFragment extends Fragment {

    private static final String TAG = "InfoFragment";

    private EditText nameEdit;

    private RadioGroup sexGroup;

    private RadioButton sexMale;

    private RadioButton sexFemale;

    private EditText ageEdit;

    private EditText weightEdit;

    private EditText cityEdit;

    private EditText workEdit;

    private EditText contentEdit;

    private Button submitBtn;

    private InfoViewModel infoViewModel;

    private MainActivity mainActivity;

    private boolean updateFlag;

    private Info info;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //因为传参都是同一个 mainActivity,所以这里获取到的 infoViewModel 和 MainFragment 中的是同一个
        infoViewModel = ViewModelProviders.of(mainActivity).get(InfoViewModel.class);
        infoViewModel.getUpdateFlag().observe(this, flag -> {
            if (flag == null) {
                AppLog.debug(TAG, "getUpdateFlag() flag == null");
            } else {
                updateFlag = flag;
                if (updateFlag) {
                    submitBtn.setText("修改");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        submitBtn.setOnClickListener(v -> {
            Info info1 = new Info();
            info1.setName(nameEdit.getText().toString());
            info1.setSex(sexMale.isChecked() ? true : false);
            info1.setAge(Integer.parseInt(ageEdit.getText().toString()));
            info1.setWeight(Double.parseDouble(ageEdit.getText().toString()));
            info1.setCity(cityEdit.getText().toString());
            info1.setJob(workEdit.getText().toString());
            info1.setComment(contentEdit.getText().toString());
            if (updateFlag) {
                info1.setNumber(info.getNumber());
                infoViewModel.updateInfos(info1);
            } else {
                infoViewModel.insertInfos(info1);
            }
            infoViewModel.setUpdateFlag(false);
            mainActivity.onBackPressed();        //销毁当前的 fragment
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void updateInfo(Info info) {
        this.info = info;
    }

    private void initView(View view) {
        nameEdit = view.findViewById(R.id.name);
        sexGroup = view.findViewById(R.id.sex_group);
        sexMale = view.findViewById(R.id.sex_male);
        sexFemale = view.findViewById(R.id.sex_female);
        ageEdit = view.findViewById(R.id.age);
        weightEdit = view.findViewById(R.id.weight);
        cityEdit = view.findViewById(R.id.city);
        workEdit = view.findViewById(R.id.work);
        contentEdit = view.findViewById(R.id.content1);
        submitBtn = view.findViewById(R.id.submit_btn);

        if (info != null) {
            nameEdit.setText(info.getName());
            sexMale.setChecked(info.getSex());
            sexFemale.setChecked(!info.getSex());
            ageEdit.setText(String.valueOf(info.getAge()));
            weightEdit.setText(String.valueOf(info.getWeight()));
            cityEdit.setText(info.getCity());
            workEdit.setText(info.getJob());
            contentEdit.setText(info.getComment());
        }
    }
}
