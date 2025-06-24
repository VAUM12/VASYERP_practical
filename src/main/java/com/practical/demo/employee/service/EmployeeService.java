package com.practical.demo.employee.service;
import com.practical.demo.employee.dto.EmployeeDto;
import com.practical.demo.employee.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Page<EmployeeDto> getAllEmployees(Pageable pageable);
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto createEmployee(Employee employee);
    EmployeeDto updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    List<EmployeeDto> getEmployeesWithSalaryAboveAverage();
    List<Object[]> getEmployeeCountByDepartment();
}

