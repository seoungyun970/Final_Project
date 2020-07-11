package com.example.myapplication.Server;

import com.example.myapplication.VO.SunhansVO;

public class DataManager {

        private static volatile com.example.myapplication.Server.DataManager DataManager = null;

        private DataManager(){}
        public static com.example.myapplication.Server.DataManager getInstance() {
            if (DataManager == null) {
                synchronized (com.example.myapplication.Server.DataManager.class) {
                    if (DataManager == null) {
                        DataManager = new DataManager();
                    }
                }
            }
            return DataManager;
        }
        SunhansVO LoginUser;

        public void  SetLoginUser(SunhansVO loginuser){
            this.LoginUser=loginuser;
        }
        public SunhansVO GetLoginUser(){
             return this.LoginUser;
         }
}
