package com.example.myapplication.VO;

import java.io.Serializable;

public class ChildChatVO implements Serializable {
    String chatTime;
    String FromID;          //상대방 아이디
    String profile;
    String toprofile;
    String ChatContent;
    String ToID;

    public String getToprofile() {
        return toprofile;
    }

    public void setToprofile(String toprofile) {
        this.toprofile = toprofile;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public String getFromID() {
        return FromID;
    }

    public void setFromID(String fromID) {
        FromID = fromID;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getChatContent() {
        return ChatContent;
    }

    public void setChatContent(String chatContent) {
        ChatContent = chatContent;
    }

    public String getToID() {
        return ToID;
    }

    public void setToID(String toID) {
        ToID = toID;
    }

    @Override
    public String toString() {
        return "ChildChatVO{" +
                "chatTime='" + chatTime + '\'' +
                ", FromID='" + FromID + '\'' +
                ", profile='" + profile + '\'' +
                ", ChatContent='" + ChatContent + '\'' +
                ", ToID='" + ToID + '\'' +
                '}';
    }
}
