package com.assignment.kr_pc.pretlistassignment;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EmployeeListActivity extends AppCompatActivity {
    private static final String JSON_URL = "http://api.myjson.com/bins/1fblnq";
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view);
    }

    public boolean isConnectivityAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onLoadEmployeeButtonClick(View view) {
        if(!isConnectivityAvailable()) {
            Toast.makeText(getApplicationContext(), R.string.turn_on_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.GONE);

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            //we have the array named employee inside the object
                            //so here we are getting that json array
                            JSONArray employeeArray = obj.getJSONArray("employees");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < employeeArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject employeeObject = employeeArray.getJSONObject(i);

                                //creating a employee object and giving them the values from json object
                                Employee employee = new Employee(employeeObject.getString("empId"), employeeObject.getString("name"),
                                        employeeObject.getString("department"));

                                //adding the employee to employeelist
                                employeeList.add(employee);
                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);

                            //creating custom adapter object
                            CustomAdapter adapter = new CustomAdapter(employeeList);
                            recyclerView.setAdapter(adapter);

                            //adding the adapter to recycler view
                            recyclerView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrsa
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
