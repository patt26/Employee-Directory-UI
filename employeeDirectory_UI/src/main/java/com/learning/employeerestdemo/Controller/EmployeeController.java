package com.learning.employeerestdemo.Controller;

import com.learning.employeerestdemo.Model.Employee;
import com.learning.employeerestdemo.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Controller
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService empl) {
        this.employeeService = empl;
    }

    @GetMapping("/employees")
    public String find(Model model) {
        List<Employee> theEmployees= employeeService.getAllEmployees();

        // to add in spring model
        model.addAttribute("employee",theEmployees);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Employee empl=new Employee();
        model.addAttribute("employees",empl);
        return "employees/employees-form";
    }

    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/NewEmployees")
    public String createEmployees(@ModelAttribute("employees") Employee empl) {
          employeeService.createEmployee(empl);

          return  "redirect:/employees";
    }

    @PostMapping("/BulkEmployees")
    public ResponseEntity<List<Employee>> createMultiples(@RequestBody List<Employee> empl){
        List<Employee> empl1=employeeService.createMultiple(empl);
        return ResponseEntity.ok(empl1);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
       Employee empl= employeeService.getEmployeeById(id);
       if(empl==null){
           return new ResponseEntity<>("No record with given id found",HttpStatus.NOT_FOUND);
       }
       employeeService.DeleteEmployee(id);
       return  status(HttpStatus.OK).body("User deleted successfully");

    }


    @PutMapping("/employees/{id}")
    public Employee UpdateById(@RequestBody Employee emp, @PathVariable(value = "id") int id){
      emp.setId(id);
      return employeeService.updateEmployee(emp);
    }

    @GetMapping("employees/sort/{props}")
    public List<Employee> sorting(@PathVariable String props, @RequestParam (required = false)  String order ){
        if(order==null){
            order="asc";
        }
        return employeeService.sortEmployees(props,order);


        // localhost:8080/api/employees/sort/prop?order=desc use this endpoint for query result
        // otherwise use regular

    }
}