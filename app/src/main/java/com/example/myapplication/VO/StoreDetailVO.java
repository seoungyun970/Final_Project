package com.example.myapplication.VO;

import java.io.Serializable;

public class StoreDetailVO implements Serializable {
    int result;
    String open1;
    String now;
    String open2;
    String close1;
    String close2;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getOpen1() {
        return open1;
    }

    public void setOpen1(String open1) {
        this.open1 = open1;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getOpen2() {
        return open2;
    }

    public void setOpen2(String open2) {
        this.open2 = open2;
    }

    public String getClose1() {
        return close1;
    }

    public void setClose1(String close1) {
        this.close1 = close1;
    }

    public String getClose2() {
        return close2;
    }

    public void setClose2(String close2) {
        this.close2 = close2;
    }

    @Override
    public String toString() {
        return "StoreDetailVO{" +
                "result=" + result +
                ", open1='" + open1 + '\'' +
                ", now='" + now + '\'' +
                ", open2='" + open2 + '\'' +
                ", close1='" + close1 + '\'' +
                ", close2='" + close2 + '\'' +
                '}';
    }
}
