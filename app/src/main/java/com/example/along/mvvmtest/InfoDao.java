package com.example.along.mvvmtest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface InfoDao {

    //定义可变形参，这样方便插入一个或多个值，onConflict = OnConflictStrategy.REPLACE 表示插入有冲突时，覆盖掉旧值。
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInfos(Info... infos);

    //Update方法在数据库中用于修改一组实体的字段。它使用每个实体的主键来匹配查询。
    @Update
    void updateInfos(Info... infos);

    //Delete方法用于从数据库中删除给定参数的一系列实体，它使用主键匹配数据库中相应的行。
    @Delete
    void deleteInfos(Info... infos);

    //根据姓名查询信息，将查询到的信息按年龄升序排序
    //其实 插入，更新和删除也可以用 @Query 修饰，括号里面写上对应的 sql 语句就可以。
    @Query("SELECT * FROM info_table WHERE name LIKE :name ORDER BY age ASC")
    List<Info> findInfosByName(String name);

    @Query("SELECT * FROM info_table")
    List<Info> findInfos();

}
