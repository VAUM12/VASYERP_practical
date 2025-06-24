package com.practical.demo.util;

import com.practical.demo.employee.model.Employee;
import com.practical.demo.employee.repository.EmployeeRepository;
import com.practical.demo.user.model.UserInfo;
import com.practical.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Admin user
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            UserInfo admin = new UserInfo();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("Hello@1234"));
            admin.setRoles("ROLE_ADMIN");
            userRepository.save(admin);
        }

        // Regular user
        if (!userRepository.existsByEmail("user@gmail.com")) {
            UserInfo user = new UserInfo();
            user.setName("User");
            user.setEmail("user@gmail.com");
            user.setPassword(passwordEncoder.encode("Hello@1234"));
            user.setRoles("ROLE_USER");
            userRepository.save(user);
        }

        // Dummy employees
        if (employeeRepository.count() == 0) {
            employeeRepository.saveAll(List.of(
                    new Employee(null, "John Doe", "IT", 50000.0),
                    new Employee(null, "Jane Smith", "HR", 60000.0),
                    new Employee(null, "Alex Brown", "Finance", 45000.0)
            ));
        }

        System.out.println("✅ Admin, User, and Dummy Employees initialized successfully.");
    }
}
