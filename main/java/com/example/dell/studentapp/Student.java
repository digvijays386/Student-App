package com.example.dell.studentapp;

import android.content.pm.PackageManager;

/**
 * Created by dell on 1/16/2018.
 */

public class Student {
    private int id;
    private  String name;
    private  String phone;
    private  String address;
    private String pincode;
    private String videoid;
    private byte[] image;

    public Student(int id, String name, String phone, String address, byte[] image,String pincode, String videoid) {
        this.id= id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.pincode= pincode;
        this.videoid= videoid;
    }

    public Student(byte[] image) {
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }
}
