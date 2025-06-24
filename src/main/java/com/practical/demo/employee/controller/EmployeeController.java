package com.practical.demo.employee.controller;

import com.practical.demo.employee.dto.EmployeeDto;
import com.practical.demo.employee.model.Employee;
import com.practical.demo.employee.service.EmployeeService;
import com.practical.demo.util.ApiResponse;
import com.practical.demo.util.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee Management API", description = "CRUD operations and queries on employees")
@SecurityRequirement(name = "Authorization")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Get all employees with pagination and sorting", description = "Accessible by USER and ADMIN")
    @GetMapping("/employees")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PagedResponse<EmployeeDto>>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<EmployeeDto> employeePage = employeeService.getAllEmployees(pageable);
        PagedResponse<EmployeeDto> simplified = new PagedResponse<>(
                employeePage.getContent(),
                employeePage.getNumber(),
                employeePage.getSize(),
                employeePage.getTotalElements(),
                employeePage.getTotalPages()
        );

        return ResponseEntity.ok(
                new ApiResponse<>("success", "Fetched employees", simplified, request.getRequestURI())
        );
    }

    @Operation(summary = "Get employee by ID", description = "Accessible by USER and ADMIN")
    @GetMapping("/employees/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(@PathVariable Long id, HttpServletRequest request) {
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Employee found", employee, request.getRequestURI())
        );
    }

    @Operation(summary = "Create new employee", description = "Accessible by ADMIN only")
    @PostMapping("/employees")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(@RequestBody Employee employee, HttpServletRequest request) {
        EmployeeDto saved = employeeService.createEmployee(employee);
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Employee created successfully", saved, request.getRequestURI())
        );
    }

    @Operation(summary = "Update employee", description = "Accessible by ADMIN only")
    @PutMapping("/employees/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(@PathVariable Long id, @RequestBody Employee employee, HttpServletRequest request) {
        EmployeeDto updated = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Employee updated successfully", updated, request.getRequestURI())
        );
    }

    @Operation(summary = "Delete employee", description = "Accessible by ADMIN only")
    @DeleteMapping("/employees/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Long id, HttpServletRequest request) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Employee deleted successfully", "Deleted ID: " + id, request.getRequestURI())
        );
    }

    @Operation(summary = "Get employees with salary above average", description = "Accessible by ADMIN only")
    @GetMapping("/employees/salary-above-average")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getEmployeesWithSalaryAboveAverage(HttpServletRequest request) {
        List<EmployeeDto> employees = employeeService.getEmployeesWithSalaryAboveAverage();
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Employees with salary above average", employees, request.getRequestURI())
        );
    }

    @Operation(summary = "Get employee count grouped by department", description = "Accessible by ADMIN only")
    @GetMapping("/employees/department-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Object[]>>> getEmployeeCountByDepartment(HttpServletRequest request) {
        List<Object[]> result = employeeService.getEmployeeCountByDepartment();
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Employee count by department", result, request.getRequestURI())
        );
    }
}
