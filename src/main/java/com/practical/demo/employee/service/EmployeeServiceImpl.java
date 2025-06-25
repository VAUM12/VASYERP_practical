package com.practical.demo.employee.service;

import com.practical.demo.employee.dto.EmployeeDto;
import com.practical.demo.employee.model.Employee;
import com.practical.demo.employee.repository.EmployeeRepository;
import com.practical.demo.exception.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
        log.info("Fetching all employees with pagination: page={}, size={}, sort={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        List<EmployeeDto> dtoList = employeePage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, employeePage.getTotalElements());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee not found with ID: {}", id);
                    return new ExceptionResponse("Employee not found with id: " + id, HttpStatus.NOT_FOUND);
                });

        return mapToDto(employee);
    }

    @Override
    public EmployeeDto createEmployee(Employee employee) {
        log.info("Creating new employee: {}", employee.getName());

        employee.setId(null);
        Employee saved = employeeRepository.save(employee);

        log.info("Employee created with ID: {}", saved.getId());
        return mapToDto(saved);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, Employee updatedEmployee) {
        log.info("Updating employee with ID: {}", id);

        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee not found for update with ID: {}", id);
                    return new ExceptionResponse("Employee not found with id: " + id, HttpStatus.NOT_FOUND);
                });

        existing.setName(updatedEmployee.getName());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setSalary(updatedEmployee.getSalary());

        Employee saved = employeeRepository.save(existing);
        log.info("Employee updated successfully: ID {}", saved.getId());

        return mapToDto(saved);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Attempting to delete employee with ID: {}", id);

        if (!employeeRepository.existsById(id)) {
            log.warn("Employee not found for deletion with ID: {}", id);
            throw new ExceptionResponse("Employee not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        employeeRepository.deleteById(id);
        log.info("Employee deleted successfully: ID {}", id);
    }

    @Override
    public List<EmployeeDto> getEmployeesWithSalaryAboveAverage() {
        log.info("Fetching employees with salary above average");

        return employeeRepository.findEmployeesWithSalaryAboveAverage()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getEmployeeCountByDepartment() {
        log.info("Fetching employee count grouped by department");
        return employeeRepository.countEmployeesByDepartment();
    }

    private EmployeeDto mapToDto(Employee e) {
        return new EmployeeDto(e.getId(), e.getName(), e.getDepartment(), e.getSalary());
    }
}
