package com.example.myapplication.VO;

import java.io.Serializable;

public class StoreVO implements Serializable {
    private String id;
    private String storeName;
    private String sendData;
    private String detail;
    private String topMessage;
    private String openTime;
    private String storephone;
    private String foodCheck;
    private String comments;
    private String price;
    private String closeTime;
    private String area;
    private String information;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(String topMessage) {
        this.topMessage = topMessage;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getStorephone() {
        return storephone;
    }

    public void setStorephone(String storephone) {
        this.storephone = storephone;
    }

    public String getFoodCheck() {
        return foodCheck;
    }

    public void setFoodCheck(String foodCheck) {
        this.foodCheck = foodCheck;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "StoreVO{" +
                "id='" + id + '\'' +
                ", storeName='" + storeName + '\'' +
                ", sendData='" + sendData + '\'' +
                ", detail='" + detail + '\'' +
                ", topMessage='" + topMessage + '\'' +
                ", openTime='" + openTime + '\'' +
                ", storephone='" + storephone + '\'' +
                ", foodCheck='" + foodCheck + '\'' +
                ", comments='" + comments + '\'' +
                ", price='" + price + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", area='" + area + '\'' +
                ", information='" + information + '\'' +
                '}';
    }
}
