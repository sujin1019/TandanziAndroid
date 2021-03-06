package com.example.kita.tandanziandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText et1, et2, et3;
    String userid, pw, weight, jsonText;
    HttpURLConnection conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.id);
        et2 = findViewById(R.id.pw);
        et3 = findViewById(R.id.weight);
    }

    public void join(View view) {
        //    String addr = "http://203.233.199.77:8889/tandanzi/join";
        int id = view.getId();

        userid = et1.getText().toString().trim();
        pw = et2.getText().toString().trim();
        weight = et3.getText().toString().trim();

        if (userid.length() == 0 || pw.length() == 0 || weight.length() == 0) {
            Toast.makeText(this, "값을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (id) {
            case R.id.btnJoin:
                JoinThread joinThread = new JoinThread();
                joinThread.start();

                break;
        }

    }

    class JoinThread extends Thread {
        String addr = "http://10.10.8.22:9010/tandanzi/join";

        @Override
        public void run() {
            super.run();
            String str = "";
            str = setResult();
            Message msg = handler.obtainMessage();
            msg.obj = str; // 메시지에 서버에서 가져온 정보 담기
            handler.sendMessage(msg);
        }// run()



        public String setResult() {

            StringBuilder sb = new StringBuilder();

            try {
                URL url = new URL(addr + "?id=" + userid + "&pw=" + pw + "&weight=" + weight);
                conn = (HttpURLConnection) url.openConnection();
                if (conn != null) {
                    conn.setUseCaches(false); // 캐시메모리를 쓰지 않겠다.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStreamReader in = new InputStreamReader(conn.getInputStream());
                        int ch;
                        while ((ch = in.read()) != -1) {
                            sb.append((char) ch);
                        }
                        in.close();
                    }
                }
                conn.disconnect();
            } catch (Exception e) {
            }

            return sb.toString();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg); // 이 안에 있는 데이터를 TextView 에 뿌려준다.
            if (msg.obj.toString().equals("success")) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("성공", "가입 성공");
                setResult(RESULT_OK,intent);
                finish();
            } else if (msg.obj.toString().equals("failed")) {
                Toast.makeText(MainActivity.this, "다른 아이디를 사용해주세요", Toast.LENGTH_SHORT).show();
            }


        }
    };


}



