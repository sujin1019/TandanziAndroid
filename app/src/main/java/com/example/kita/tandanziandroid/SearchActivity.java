package com.example.kita.tandanziandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    Intent intent;
    TextView tx1, tx2, tx3, tx4, tx5;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        intent = getIntent();

        tx1 = (TextView) findViewById(R.id.foodName);
        tx2 = (TextView) findViewById(R.id.cal);
        tx3 = (TextView) findViewById(R.id.car);
        tx4 = (TextView) findViewById(R.id.pro);
        tx5 = (TextView) findViewById(R.id.fat);

        SearchActivity.ShowResults thread = new SearchActivity.ShowResults();
        thread.start();
    }

    class ShowResults extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                tx1.setText(intent.getStringExtra("name"));
                tx2.setText(intent.getStringExtra("cal"));
                tx3.setText(intent.getStringExtra("car"));
                tx4.setText(intent.getStringExtra("pro"));
                tx5.setText(intent.getStringExtra("fat"));
            }catch (Exception e){}
        }// run()
    }
}
