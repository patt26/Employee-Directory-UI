package com.learning.employeeUI.Interface;

import com.learning.employeeUI.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
        List<Employee> findAllByOrderByFirstNameAsc();
        List<Employee> findAllByOrderByEmailAsc();
        List<Employee> findAllByOrderByLastNameAsc();

        List<Employee> findAllByOrderByFirstNameDesc();
        List<Employee> findAllByOrderByEmailDesc();
        List<Employee> findAllByOrderByLastNameDesc();

}
