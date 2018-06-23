package com.assignment.kr_pc.pretlistassignment;

class Employee {
    private String empId;
    private String empName;
    private String empDepartment;

    public Employee(String empId, String name, String department) {
        this.empId = empId;
        this.empName = name;
        this.empDepartment = department;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpDepartment() {
        return empDepartment;
    }
}
