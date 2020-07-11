package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText editTxt_ID;
    EditText editTxt_PW;
    Button btn_Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        editTxt_ID = (EditText)findViewById(R.id.editTxt_ID);
        editTxt_PW = (EditText)findViewById(R.id.editTxt_PW);
        btn_Register = (Button)findViewById(R.id.btn_Register);
        btn_Register.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_Register:
                RegisterUser task = new RegisterUser();
                task.execute(editTxt_ID.getText().toString(), editTxt_PW.getText().toString()); //1번
                break;
        }
    }
    private class RegisterUser  extends AsyncTask<String, String, String>
    {
        ProgressDialog loading;
        URL register_url;

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Register.this, "Please Wait", null, true, true);
        }

        @Override
        //2번
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/regis.jsp";

                String _id = (String) params[0];
                String _pw = (String) params[1];

                System.out.println(_id);
                System.out.println(_pw);

                String url_address = URL + "?id=" + _id + "&pw=" + _pw;

                register_url = new URL(url_address);
                BufferedReader in = new BufferedReader(new InputStreamReader(register_url.openStream()));

                String result = "";
                String temp = "";
                //readLine()시 리턴값을 String으로 고정되기에 String이 아닌 다른타입으로 입력을 받을려면 형변환을 꼭 해주어야한다는 점
                while ((temp = in.readLine()) != null) {
                    result += temp;
                }
                in.close();

                return result;
            }
            catch (Exception e)
            {
                System.out.println("실패");
                return new String("Exception : " + e.getMessage());
            }

        }

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            loading.dismiss();
            System.out.println(result);
            if(result.contains("success"))
            {
                Intent intent = new Intent(Register.this, Register.class);
                startActivity(intent);
            }
        }
    }
}
