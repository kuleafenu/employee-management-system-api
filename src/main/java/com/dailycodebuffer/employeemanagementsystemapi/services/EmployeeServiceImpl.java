package com.dailycodebuffer.employeemanagementsystemapi.services;

import com.dailycodebuffer.employeemanagementsystemapi.entity.EmployeeEntity;
import com.dailycodebuffer.employeemanagementsystemapi.model.Employee;
import com.dailycodebuffer.employeemanagementsystemapi.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();

        BeanUtils.copyProperties(employee, employeeEntity);

         employeeRepository.save(employeeEntity);

         return employee;

    }

    @Override
    public List<Employee> getAllEmployees() {

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        List<Employee> employees = employeeEntities.stream()
                .map(employee -> new Employee(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmailId()
                ))
                .collect(Collectors.toList());

        return employees;
    }

    @Override
    public boolean deleteEmployee(Long id) {

        EmployeeEntity employee = employeeRepository.findById(id).get();

        employeeRepository.delete(employee);

        return true;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        EmployeeEntity employeeEntity  = employeeRepository.findById(id).get();

        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeEntity, employee);

        return employee;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {

        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();

        employeeEntity.setEmailId(employee.getEmailId());
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());

        employeeRepository.save(employeeEntity);
        return employee;
    }
}
