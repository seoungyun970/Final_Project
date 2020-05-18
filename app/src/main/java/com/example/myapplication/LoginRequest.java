package com.example.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://3.12.173.221:8080/SunhanWeb/andlogin.jsp";
    private Map<String, String> map;

    public LoginRequest(String _id, String _pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("_id", _id);
        map.put("_pw", _pw);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}