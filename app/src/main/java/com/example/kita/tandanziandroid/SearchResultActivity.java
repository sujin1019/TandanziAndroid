package com.example.kita.tandanziandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> listData;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);

        intent = getIntent();
        listData = new ArrayList<>();
        SearchResultActivity.ShowResults thread = new SearchResultActivity.ShowResults();
        thread.start();
    }

    class ShowResults extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                listData = new ArrayList<>();

                StringBuilder sb = new StringBuilder();
                JSONArray jsonArray = null; //json 데이터가 담긴 배열
                JSONObject item = null; // 배열 각각의 아이템

                jsonArray = new JSONArray(intent.getStringExtra("contents"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    item = jsonArray.getJSONObject(i);
                    listData.add(item.getString("name"));
                }
            } catch (Exception e) {
            }

            // Adapter 준비
            MyAdapter adapter = new MyAdapter();

            // ListView 에 set 하기
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

        }// run()
    }// Thread()

    class MyAdapter extends BaseAdapter {

        // 준비된 데이터의 개수 리턴
        @Override
        public int getCount() {
            Log.v("getCount()=====>", listData.size() + ""); // msg는 문자열만 가능
            return listData.size(); // or data.length 리턴 ==> 리스트에 보여주는 개수
        }

        @Override          // position: 몇 번 방 데이터를 요청하는지
        public Object getItem(int position) {
            Log.v("getItem()=====>", listData.get(position));
            return listData.get(position);
        }

        @Override
        public long getItemId(int i) {
            Log.v("getItemId()=====>", i + "");
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //     Log.v("getView()====>", "호출됨");
            TextView textView = new TextView(SearchResultActivity.this);
            textView.setText(listData.get(i));
            textView.setTextSize(25.0f);
            textView.setPadding(30, 30, 30, 30);

            if(i%3==0){
                textView.setTextColor(Color.GREEN);
            }else if(i%3==1){
                textView.setTextColor(Color.RED);
            }else{
                textView.setTextColor(Color.YELLOW);
            }

            return textView;
        }
    }
}
