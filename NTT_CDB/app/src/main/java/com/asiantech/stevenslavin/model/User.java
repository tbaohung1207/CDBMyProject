package com.asiantech.stevenslavin.model;

import java.io.Serializable;

/**
 * Created by StevenSlaavin on 5/29/2015.
 */
public class User implements Serializable {
    private String name;
    private String avatarid;
    private String gender;
    private String age;
    private String job;
    private String city;
    public User(){
    }

    public User(String name, String gender, String avatarid, String age, String job,String city) {
        this.name = name;
        this.gender = gender;
        this.avatarid = avatarid;
        this.age = age;
        this.job = job;
        this.city = city;
    }

    public String getAvatarid() {
        return avatarid;
    }

    public void setAvatarid(String avatar) {
        this.avatarid = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
