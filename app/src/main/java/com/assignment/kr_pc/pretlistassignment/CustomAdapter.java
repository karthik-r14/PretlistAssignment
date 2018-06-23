package com.assignment.kr_pc.pretlistassignment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private ArrayList<Employee> employeeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView empId, empName, empDept;

        public MyViewHolder(View view) {
            super(view);
            empId = view.findViewById(R.id.empId);
            empName = view.findViewById(R.id.empName);
            empDept = view.findViewById(R.id.empDept);
        }

        void bind(int listIndex) {
            empId.setText(employeeList.get(listIndex).getEmpId().toString());
            empName.setText(employeeList.get(listIndex).getEmpName());
            empDept.setText(employeeList.get(listIndex).getEmpDept());
        }
    }

    public CustomAdapter(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }
}
