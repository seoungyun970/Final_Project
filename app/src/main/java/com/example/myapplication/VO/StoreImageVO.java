package com.example.myapplication.VO;

import java.io.Serializable;

public class StoreImageVO implements Serializable {
    private String fileRealName;
    private String fileRealName1;
    private String fileRealName2;

    public String getFileRealName() {
        return fileRealName;
    }

    public void setFileRealName(String fileRealName) {
        this.fileRealName = fileRealName;
    }

    public String getFileRealName1() {
        return fileRealName1;
    }

    public void setFileRealName1(String fileRealName1) {
        this.fileRealName1 = fileRealName1;
    }

    public String getFileRealName2() {
        return fileRealName2;
    }

    public void setFileRealName2(String fileRealName2) {
        this.fileRealName2 = fileRealName2;
    }

    @Override
    public String toString() {
        return "StoreImageVO{" +
                "fileRealName='" + fileRealName + '\'' +
                ", fileRealName1='" + fileRealName1 + '\'' +
                ", fileRealName2='" + fileRealName2 + '\'' +
                '}';
    }
}
