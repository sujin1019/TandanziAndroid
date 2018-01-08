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
import java.util.ArrayList;


public class LoggedActivity extends AppCompatActivity {
    EditText et;
    String word;
    HttpURLConnection conn;
    String foodList;
    String slType;
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

    public void getFoodList(View view){
        //여기서부터 어레이리스트 만들기.
        int id = view.getId();
        switch (id){
            case R.id.btnKorea:
                slType="한식";
                break;
            case R.id.btnJapan:
                slType="일식";
                break;
            case R.id.btnChina:
                slType="중식";
                break;
            case R.id.btnWestern:
                slType="양식";
                break;
            case R.id.btnDrink:
                slType="음료";
                break;
            case R.id.btnDessert:
                slType="디저트";
                break;
        }
        LoggedActivity.GetListThread GetListThread = new LoggedActivity.GetListThread();
        GetListThread.start();





    }

    class SearchThread extends Thread {

        //String addr = "http://192.168.35.53:9010/tandanzi/search";
            String addr = "http://192.168.35.53:9010/tandanzi/search";


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

    class GetListThread extends Thread{
        String addr = "http://192.168.35.53:9010/tandanzi/getFoodList?slType="+slType;
        public void run(){
            try {
                URL url = new URL(addr);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(1000);

                Log.v("Response Code => ",conn.getResponseCode()+"");
                StringBuilder sb = new StringBuilder();

                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    int ch;
                    while((ch=in.read())!=-1){
                        sb.append((char)ch);
                    }

                    Log.v("서버에서 온 데이터",sb.toString());

                    Message message = handler2.obtainMessage();
                    message.obj = sb.toString();
                    handler2.sendMessage(message);

                    in.close();
                    conn.disconnect();
                }

            }catch (Exception e){}
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
    Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = (String)msg.obj;
            Log.v("메시지값handler2:",message);
                foodList = message;
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                intent.putExtra("userList",foodList);
                setResult(RESULT_OK,intent);
                Log.v("intent값:",message);
                startActivity(intent);

        }
    };

}