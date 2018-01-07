package com.example.kita.tandanziandroid;

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

/**
 * Created by charlotte on 2018-01-04.
 */

public class LoggedActivity extends AppCompatActivity {
    EditText et;
    String word;
    HttpURLConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        et = findViewById(R.id.searchContent);
        Intent intent = getIntent();
    }


    public void search(View view) {
        int id = view.getId();

        word = et.getText().toString().trim();

        if (word.length() == 0) {
            Toast.makeText(this, "값을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (id) {
            case R.id.btnSearch:
                LoggedActivity.SearchThread SearchThread = new LoggedActivity.SearchThread();
                SearchThread.start();

                break;
        }
    }

    class SearchThread extends Thread {

        //String addr = "http://192.168.35.53:9010/tandanzi/search";
            String addr = "http://10.10.12.34:8888/tandanzi/search";


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
                URL url = new URL(addr + "?foodname=" + word);
                Log.v("word", word);
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
            Toast.makeText(LoggedActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();

/*
            if (msg.obj.toString().equals("success")) {
                Intent intent = new Intent(LoggedActivity.this, LoginActivity.class);
                intent.putExtra("성공", "가입 성공");
                setResult(RESULT_OK, intent);
                finish();
            } else if (msg.obj.toString().equals("failed")) {
                Toast.makeText(LoggedActivity.this, "다른 아이디를 사용해주세요", Toast.LENGTH_SHORT).show();
            }
*/


        }
    };

}