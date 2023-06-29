package com.example.prm392_project_2.dtos;

public class Account extends BaseEntity{
    private String username = "";
    private String password = "";
    private String fullname = "";
    private String avatar = null;

    public Account() {
        super();
    }

    public Account( String username, String password, String fullname, String avatar) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.avatar = avatar;
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
