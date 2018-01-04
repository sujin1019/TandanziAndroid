package com.example.kita.tandanziandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText et1, et2, et3;
    String userid, pw, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.id);
        et2 = findViewById(R.id.pw);
        et3 = findViewById(R.id.weight);
    }

    public void join(View view){
    //    String addr = "http://203.233.199.77:8889/tandanzi/join";
        int id = view.getId();

        userid = et1.getText().toString().trim();
        pw = et2.getText().toString().trim();
        weight = et3.getText().toString().trim();

        if(userid.length()==0 || pw.length()==0 || weight.length()==0){
            Toast.makeText(this, "값을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (id){
            case R.id.btnJoin:
                JoinThread joinThread = new JoinThread();
                joinThread.start();
                break;
        }

    }

    class JoinThread extends Thread{

        String addr = "http://10.10.8.22:9010/tandanzi/join";

        @Override
        public void run() {
            super.run();
            try {
                URL url = new URL(addr+"?id="+userid+"&pw="+pw+"&weight="+weight);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setUseCaches(false); // 캐시메모리를 쓰지 않겠다.
                    if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                        Log.v("접속", "접속 성공");
                    }
                }
            }catch (Exception e){}
        }

    }

}
