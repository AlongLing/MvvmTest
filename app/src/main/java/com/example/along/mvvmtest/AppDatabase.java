package com.example.along.mvvmtest;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Info.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase sInstance;

    //自定义的 database 类最好使用单例模式，创建多个无意义且消耗内存
    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "info_database").build();
                }
            }
        }
        return sInstance;
    }

    //定义抽象方法， room 会实例化该接口
    public abstract InfoDao infoDao();

}
