package com.practical.demo.employee.dto;

public class EmployeeDto {
    private Long id;
    private String name;
    private String department;
    private Double salary;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDto(Long id, String name, String department, Double salary) {
        this.department = department;
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}
