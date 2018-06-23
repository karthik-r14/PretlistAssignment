package com.assignment.kr_pc.pretlistassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onProblemOneClick(View view) {
        Intent intent = new Intent(MainActivity.this, EmployeeListActivity.class);
        startActivity(intent);
    }

    public void onProblemTwoClick(View view) {
        Intent intent = new Intent(MainActivity.this, WifiInfoActivity.class);
        startActivity(intent);
    }
}
