
package com.sdet.employeemanagement.service;

import com.sdet.employeemanagement.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    // Temporary in-memory employee storage
    private final List<Employee> employees = new ArrayList<>();

    // Generate employee IDs
    private int nextId = 3;

    // Constructor - Initial employee data
    public EmployeeService() {

        employees.add(new Employee(
                1, "John", "john@gmail.com", "IT", 75000
        ));

        employees.add(new Employee(
                2, "Sam", "sam@gmail.com", "HR", 65000
        ));
    }

    // GET - Retrieve all employees
    public List<Employee> getAllEmployees() {
        return employees;
    }

    // GET - Retrieve employee by ID
    public Employee getEmployeeById(int id) {

        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // POST - Add a new employee
    public Employee addEmployee(Employee employee) {

        employee.setId(nextId++);
        employees.add(employee);

        return employee;
    }

    // PUT - Update employee
    public Employee updateEmployee(int id, Employee updatedEmployee) {

        Employee existingEmployee = getEmployeeById(id);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setSalary(updatedEmployee.getSalary());

        return existingEmployee;
    }

    // DELETE - Remove employee
    public boolean deleteEmployee(int id) {

        Employee employee = getEmployeeById(id);

        if (employee == null) {
            return false;
        }

        employees.remove(employee);

        return true;
    }
}
