package com.example.along.mvvmtest;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.along.mvvmtest.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

public class InfoRepository {

    private static final String TAG = "InfoRepository";

    private static InfoRepository sInstance;

    private InfoDao infoDao;

    private MutableLiveData<List<Info>> infoList = new MutableLiveData<>();

    private List<Info> infoListTemp = new ArrayList<>();

    private InfoRepository(Application application) {
        AppLog.debug(TAG, "InfoRepository() construct method");
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        infoDao = appDatabase.infoDao();
        //查找 info_table 表中的所有数据, 返回值是一个 livedata 值，如果该数据有变更的话能够通知给观察者。
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                infoListTemp = infoDao.findInfos();
            }
        });
        thread1.start();
        try {
            thread1.join();
            infoList.setValue(infoListTemp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static InfoRepository getInstance(Application application) {
        AppLog.debug(TAG, "getInstance()");
        if (sInstance == null) {
            synchronized (InfoRepository.class) {
                if (sInstance == null) {
                    sInstance = new InfoRepository(application);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Info>> getInfoList() {
        return infoList;
    }

    //根据姓名查找对应的信息数据
    public void findInfosByName(String name) {
        AppLog.debug(TAG, "findInfosByName(): name = " + name);
        Thread thread3 = new Thread(() -> {
            List<Info> infos = infoDao.findInfosByName(name);
            if (infos == null || infos.isEmpty()) {
                AppLog.debug(TAG, "findInfosByName(): infos == null or infos is empty");
                infoListTemp = infoDao.findInfos();
            } else {
                infoListTemp = infos;
            }
        });
        thread3.start();
        try {
            thread3.join();
            infoList.setValue(infoListTemp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //删除对应的信息数据
    public void deleteInfos(Info... infos) {
        for (Info info : infos) {
            AppLog.debug(TAG, "deleteInfos(): info = " + info.toString());
        }
        Thread thread4 = new Thread(() -> {
            infoDao.deleteInfos(infos);
            infoListTemp = infoDao.findInfos();
        });
        thread4.start();
        try {
            thread4.join();
            infoList.setValue(infoListTemp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //更新对应的信息数据
    public void updateInfos(Info... infos) {
        Thread thread5 = new Thread(() -> {
            infoDao.updateInfos(infos);
            infoListTemp = infoDao.findInfos();
        });
        thread5.start();
        try {
            thread5.join();
            infoList.setValue(infoListTemp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //插入对应的信息数据
    public void insertInfos(Info... infos) {
        for (Info info : infos) {
            AppLog.debug(TAG, "insertInfos(): info = " + info.toString());
        }
        Thread thread2 = new Thread(() -> {
            infoDao.insertInfos(infos);
            infoListTemp = infoDao.findInfos();
        });
        thread2.start();
        try {
            thread2.join();
            infoList.setValue(infoListTemp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
