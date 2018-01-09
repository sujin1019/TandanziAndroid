package com.example.kita.tandanziandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by charlotte on 2018-01-08.
 */

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView simpleList = findViewById(R.id.simpleList);
        Intent intent = getIntent();
        String result = intent.getStringExtra("userList");
        JSONArray jsonArray = null; // json 데이터가 담긴 배열
        JSONObject item  = null; // 배열 각각의 아이템
        StringBuilder sb = new StringBuilder();
        try {
            jsonArray = new JSONArray(result);
            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();

            for(int i=0;i<jsonArray.length();i++){
                item = jsonArray.getJSONObject(i);

                sb.append("이름: " );
                sb.append(item.getString("slName"));

                sb.append(", 칼로리: " );
                sb.append(item.getInt("slKcal"));

                sb.append(", 탄수화물: " );
                sb.append(item.getInt("slCarbohy"));

                sb.append(", 단백질: " );
                sb.append(item.getInt("slProtein"));

                sb.append(", 지방: " );
                sb.append(item.getInt("slFat"));
                sb.append("\n");
            }
            simpleList.setText(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v("simpleList:",simpleList.getText().toString());
    }
}