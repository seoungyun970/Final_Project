package com.example.myapplication.VO;

import java.io.Serializable;

public class ReserveStatusVO implements Serializable {
    String rs_userid; // 아동 아이디
    String rs_storeid; // 가게 아이디
    String rs_available; // 가능한지 안한지 여부 - 가능할땐 Y , 불가능할땐 N

    public String getRs_userid() {
        return rs_userid;
    }

    public void setRs_userid(String rs_userid) {
        this.rs_userid = rs_userid;
    }

    public String getRs_storeid() {
        return rs_storeid;
    }

    public void setRs_storeid(String rs_storeid) {
        this.rs_storeid = rs_storeid;
    }

    public String getRs_available() {
        return rs_available;
    }

    public void setRs_available(String rs_available) {
        this.rs_available = rs_available;
    }

    @Override
    public String toString() {
        return "ReserveStatusVO{" +
                "rs_userid='" + rs_userid + '\'' +
                ", rs_storeid='" + rs_storeid + '\'' +
                ", rs_available='" + rs_available + '\'' +
                '}';
    }
}
