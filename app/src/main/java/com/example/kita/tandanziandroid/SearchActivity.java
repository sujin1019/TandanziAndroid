package com.example.kita.tandanziandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    Intent intent;
    TextView tx1, tx2, tx3, tx4;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        intent = getIntent();

        tx1 = (TextView) findViewById(R.id.foodName);
        tx2 = (TextView) findViewById(R.id.car);
        tx3 = (TextView) findViewById(R.id.pro);
        tx4 = (TextView) findViewById(R.id.fat);

        SearchActivity.ShowResults thread = new SearchActivity.ShowResults();
        thread.start();
    }

    class ShowResults extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                StringBuilder sb = new StringBuilder();
                JSONArray jsonArray = null; //json 데이터가 담긴 배열
                JSONObject item = null; // 배열 각각의 아이템

                jsonArray = new JSONArray(intent.getStringExtra("contents"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    item = jsonArray.getJSONObject(i);
                    tx1.setText(item.getString("name"));
                    tx2.setText(item.getString("eachCarbohy"));
                    tx3.setText(item.getString("eachProtein"));
                    tx4.setText(item.getString("eachFat"));
                }
            }catch (Exception e){}
        }// run()
    }
}
