package com.assignment.kr_pc.pretlistassignment;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class EmployeeListActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("all_employees");
        employeeList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting employee
                    Employee employee = postSnapshot.getValue(Employee.class);
                    //adding employee to the list
                    employeeList.add(employee);
                    Toast.makeText(getApplicationContext(), employee.getEmpDept(), Toast.LENGTH_SHORT).show();
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);

                //creating custom adapter object
                CustomAdapter adapter = new CustomAdapter(employeeList);
                //adding the adapter to recycler view
                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
