package com.example.myapplication.VO;


public class SunhansVO {
    String id;
    String name;
    String pass;
    String addr;
    String phone;
    String email;

    String points;
    String admin;
    String Enter;
    String profile;
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getEnter() {
        return Enter;
    }

    public void setEnter(String enter) {
        Enter = enter;
    }

    @Override
    public String toString() {
        return "SunhansVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", addr='" + addr + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", points='" + points + '\'' +
                ", admin='" + admin + '\'' +
                ", Enter='" + Enter + '\'' +
                ", Profile='" + profile + '\'' +
                '}';
    }
}