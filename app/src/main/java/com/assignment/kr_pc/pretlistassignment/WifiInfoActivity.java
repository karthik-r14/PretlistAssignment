package com.assignment.kr_pc.pretlistassignment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WifiInfoActivity extends AppCompatActivity {

    public static final int MY_PERMISSION_REQUEST_CHANGE_WIFI_STATE = 123;
    private WifiManager.LocalOnlyHotspotReservation mReservation;
    WifiManager mWifiManager;
    List<ScanResult> wifiList;
    ListView wifiListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_info);
        wifiListView = findViewById(R.id.wifi_listview);
    }

    public void onWifiButtonClick(View view) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(), R.string.wifi_off_msg, Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WifiInfoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_CHANGE_WIFI_STATE);
        } else {
            myWifiMethod();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CHANGE_WIFI_STATE: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myWifiMethod();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.permission_missing, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Starting scan to detect wifi networks.
    private void myWifiMethod() {
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        getApplicationContext().registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mWifiManager.startScan();
    }

    //Declaring Broadcast receiver to detect wifi networks.
    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                wifiList = mWifiManager.getScanResults();
                ArrayList wifiData = new ArrayList();

                wifiData.add(getString(R.string.wifi_connections_msg) + wifiList.size());

                for (int i = 0; i < wifiList.size(); ++i) {
                    String wifiNetworkData = (i + 1) + "." + "SSID :" + wifiList.get(i).SSID.toString() + "\n" + "BSSID:" + (wifiList.get(i).BSSID).toString();
                    wifiData.add(wifiNetworkData);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(WifiInfoActivity.this, android.R.layout.simple_list_item_1, wifiData);
                wifiListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
