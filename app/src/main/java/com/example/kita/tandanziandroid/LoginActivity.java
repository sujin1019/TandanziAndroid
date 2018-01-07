package com.example.kita.tandanziandroid;

import android.app.Activity;
import android.content.Intent;
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
    String idText,pwText;
    static final int LOGIN_ACT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.pw);
    }

    public void join(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivityForResult(intent,LOGIN_ACT);
    }


    public void login(View view){
        idText=id.getText().toString().trim();
        pwText=password.getText().toString().trim();
        if(idText.length()==0 || pwText.length() == 0){
            Toast.makeText(this,"id와 pw를 입력하세요.",Toast.LENGTH_SHORT).show();
            return;
        }else {
            new LoginThread().start();
        }


    }
    class LoginThread extends Thread{
     //   String addr = "http://10.10.10.76:8888/tandanzi/login?id="+idText+"&pw="+pwText;
        String addr = "http://192.168.35.53:9010/tandanzi/login?id="+idText+"&pw="+pwText;
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
            Log.v("메시지값:",message);
            if(message.equals("success")){
             //   Toast.makeText(LoginActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),LoggedActivity.class);
                intent.putExtra("userId",idText);
                intent.putExtra("userPw",pwText);
                setResult(RESULT_OK,intent);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == LOGIN_ACT){
            if(resultCode == RESULT_OK){
                String userId = intent.getStringExtra("id");
                String userPw = intent.getStringExtra("pw");
                Toast.makeText(this,userId,Toast.LENGTH_LONG).show();
            }
        }

    }
}
