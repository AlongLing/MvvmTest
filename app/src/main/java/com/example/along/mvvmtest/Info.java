package com.example.along.mvvmtest;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "info_table")
public class Info {

    //定义一个自增长的主键
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "number")
    private int number;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "age")
    private int age;

    @ColumnInfo(name = "sex")
    private boolean sex;

    @ColumnInfo(name = "weight")
    private double weight;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "job")
    private String job;

    @ColumnInfo(name = "comment")
    private String comment;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Info{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", weight=" + weight +
                ", city='" + city + '\'' +
                ", job='" + job + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
