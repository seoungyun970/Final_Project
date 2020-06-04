package com.example.myapplication.JSON;

import com.example.myapplication.VO.SunhansVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JSON {
//json관리하는 클래스

    public List loginuserjsonParsing(String json)//로그인할때 호출되는 json
    {

        List<SunhansVO> list = new LinkedList<SunhansVO>(); //vo에 담아넣을 리스트 생성
        try{
            JSONObject jsonObject = new JSONObject(json);//json오브젝트생성하는데 위에 인자값인 json 이때 json은 jsp에서만든 string
            //그대로담긴다. 주의! jsp부터 잘넘어와저야한다.

            JSONArray Array = jsonObject.getJSONArray("loginuser");//지금은 loginuser json인자값 하나만 리턴하면되지만
            //나중에 엄청많은 데이터들을 배열에담아 저장하기위해 어레이로 하였다..어제고생ㅋ하하
            for(int i=0; i<Array.length(); i++)//배열에담긴만듬돌아줌
            {
                JSONObject userob = Array.getJSONObject(i);//처음배열

                SunhansVO sunhans = new SunhansVO();//처음배열에담아줄vo

                sunhans.setId(userob.getString("id"));
                sunhans.setName(userob.getString("name"));
                sunhans.setPass(userob.getString("pw"));
                sunhans.setAddr(userob.getString("addr"));
                sunhans.setPhone(userob.getString("phone"));
                sunhans.setEmail(userob.getString("email"));//전부담아줌
                sunhans.setPoints(userob.getString("points"));
                sunhans.setAdmin(userob.getString("admin"));
                sunhans.setEnter(userob.getString("enter"));

                list.add(sunhans);//넣어줌
                return list;//넣어진 리스트값 리턴
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}