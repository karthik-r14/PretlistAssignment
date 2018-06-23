package com.assignment.kr_pc.pretlistassignment;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean isConnectivityAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onProblemOneClick(View view) {
        if(!isConnectivityAvailable()) {
            Toast.makeText(getApplicationContext(), R.string.turn_on_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, EmployeeListActivity.class);
        startActivity(intent);
    }

    public void onProblemTwoClick(View view) {
        Intent intent = new Intent(MainActivity.this, WifiInfoActivity.class);
        startActivity(intent);
    }
}
