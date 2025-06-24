package com.practical.demo.employee.service;

import com.practical.demo.employee.dto.EmployeeDto;
import com.practical.demo.employee.model.Employee;
import com.practical.demo.employee.repository.EmployeeRepository;
import com.practical.demo.exception.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        List<EmployeeDto> dtoList = employeePage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, employeePage.getTotalElements());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ExceptionResponse("Employee not found with id: " + id, HttpStatus.NOT_FOUND));
        return mapToDto(employee);
    }

    @Override
    public EmployeeDto createEmployee(Employee employee) {
        employee.setId(null);
        Employee saved = employeeRepository.save(employee);
        return mapToDto(saved);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ExceptionResponse("Employee not found with id: " + id, HttpStatus.NOT_FOUND));

        existing.setName(updatedEmployee.getName());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setSalary(updatedEmployee.getSalary());

        Employee saved = employeeRepository.save(existing);
        return mapToDto(saved);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ExceptionResponse("Employee not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDto> getEmployeesWithSalaryAboveAverage() {
        return employeeRepository.findEmployeesWithSalaryAboveAverage()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getEmployeeCountByDepartment() {
        return employeeRepository.countEmployeesByDepartment();
    }

    private EmployeeDto mapToDto(Employee e) {
        return new EmployeeDto(e.getName(), e.getDepartment(), e.getSalary());
    }
}
