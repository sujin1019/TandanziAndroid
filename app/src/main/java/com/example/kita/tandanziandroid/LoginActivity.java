package com.example.kita.tandanziandroid;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText id, password;
    String i,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.pw);
    }

    public void login(View view){
        int btn = view.getId();
        i=id.getText().toString().trim();
        pw=password.getText().toString().trim();
        if(i.length()==0 || pw.length() == 0){
            Toast.makeText(this,"데이터를 입력해 주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        new LoginThread().start();



    }
    class LoginThread extends Thread{
        String addr = "http://10.10.8.20:8888/spring/login?id="+i+"&password="+pw;
        public void run(){
            try {
                URL url = new URL(addr);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");//GET 방식 명시
                conn.setConnectTimeout(1000);

                Log.v("Response Code => ",conn.getResponseCode()+"");//200이 맞는 값이므로.
                StringBuilder sb = new StringBuilder();

                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    int ch;

                    while((ch=in.read())!=-1){
                        sb.append((char)ch);
                    }

                    Log.v("서버에서 온 데이터",sb.toString());

                    //handler.sendEmptyMessage(0); 넘길 데이터가 없을떄

                    //넘길 데이터가 있을 대.
                    Message message = handler.obtainMessage();
                    message.obj = sb.toString();
                    handler.sendMessage(message);

                    in.close();
                    conn.disconnect();

                }

            }catch (Exception e){}
        }


    } //end LoginThread class

    //TextView에 데이터를 출력하는 역할 담당.
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = (String)msg.obj;

            if(message.equals("SUCCESS")){
                Toast.makeText(LoginActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
            }
        }
    };

}
