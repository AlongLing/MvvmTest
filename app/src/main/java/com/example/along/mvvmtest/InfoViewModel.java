package com.example.along.mvvmtest;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class InfoViewModel extends AndroidViewModel {

    private InfoRepository infoRepository;

    private MutableLiveData<Boolean> updateFlag = new MutableLiveData<>();

    public InfoViewModel(@NonNull Application application) {
        super(application);
        infoRepository = InfoRepository.getInstance(application);
    }

    public LiveData<List<Info>> getInfoList() {
        return infoRepository.getInfoList();
    }

    public void findInfosByName(String name) {
        infoRepository.findInfosByName(name);
    }

    public void deleteInfos(Info... infos) {
        infoRepository.deleteInfos(infos);
    }

    public void updateInfos(Info... infos) {
        infoRepository.updateInfos(infos);
    }

    public void insertInfos(Info... infos) {
        infoRepository.insertInfos(infos);
    }

    public void setUpdateFlag(boolean flag) {
        updateFlag.setValue(flag);
    }

    public LiveData<Boolean> getUpdateFlag() {
        return updateFlag;
    }
}
