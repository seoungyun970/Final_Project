package com.example.myapplication.VO;

import java.io.Serializable;

public class ChildChatDetailVO implements Serializable {
    String fromID;
    String toID;
    String chatContent;
    String chatTime;
    String myProfile;
    String totProfile;

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getMyProfile() {
        return myProfile;
    }

    public void setMyProfile(String myProfile) {
        this.myProfile = myProfile;
    }

    public String getTotProfile() {
        return totProfile;
    }

    public void setTotProfile(String totProfile) {
        this.totProfile = totProfile;
    }


    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    @Override
    public String toString() {
        return "ChildChatDetailVO{" +
                ", toID='" + toID + '\'' +
                ", chatContent='" + chatContent + '\'' +
                ", chatTime='" + chatTime + '\'' +
                ", myProfile='" + myProfile + '\'' +
                ", totProfile='" + totProfile + '\'' +
                '}';
    }

}
