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

import org.json.JSONArray;
import org.json.JSONObject;

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
        et.setPrivateImeOptions("defaultInputmode=korean;");
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
        String addr = "http://10.10.8.22:9010/tandanzi/search";
        //    String addr = "http://10.10.10.76:8888/tandanzi/join";

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
   //         Toast.makeText(LoggedActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            if(msg.obj.toString()==null){
                Toast.makeText(LoggedActivity.this, "해당 음식이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Intent intent = new Intent(LoggedActivity.this, SearchResultActivity.class);

                intent.putExtra("contents", msg.obj.toString());
                setResult(RESULT_OK, intent);
                startActivity(intent);
            }

        }
    };

}
