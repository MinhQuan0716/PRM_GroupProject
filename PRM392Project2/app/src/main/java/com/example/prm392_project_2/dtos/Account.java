package com.example.prm392_project_2.dtos;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account extends BaseEntity implements Serializable {
    private String username ;
    private String password ;
    private String fullname ;
    private String avatar ;
    private String roleName;
    private ArrayList<OrderDetail> orderDetails;
    public Account() {
        super();
    }

    public Account( String username, String password, String fullname, String avatar) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.avatar = avatar;
    }





    public Account(String username, String password, String fullname){
        this.username =username;
        this.password = password;
        this.fullname = fullname;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
