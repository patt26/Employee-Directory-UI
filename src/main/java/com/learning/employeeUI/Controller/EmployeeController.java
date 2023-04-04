package com.learning.employeeUI.Controller;

import com.learning.employeeUI.Model.Employee;
import com.learning.employeeUI.Service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Controller
//@RequestMapping("/api")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService empl) {
        this.employeeService = empl;
    }

    @GetMapping("/employees")
    public String find(Model model) {
        List<Employee> theEmployees = employeeService.getAllEmployees();

        // to add in spring model
        model.addAttribute("employee", theEmployees);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Employee empl = new Employee();
        model.addAttribute("employee", empl);
        return "employees/employees-form";
    }


    @PostMapping("/NewEmployees")
    public String createEmployees(@Valid @ModelAttribute("employees") Employee empl, BindingResult result) {
        if(result.hasErrors()){
            return "employees/employees-form";
        }
        employeeService.createEmployee(empl);
        return "redirect:employees";

    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int id, Model model) {
        Employee emp = employeeService.getEmployeeById(id);
        model.addAttribute("employee", emp);
        return "employees/employees-form";
    }

    @GetMapping("/DeleteEmployee")
    public String deleteEmployee(@RequestParam("employeeId") int id) {
        employeeService.DeleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("employees/sort/{props}")
    public String sorting(Model model,@PathVariable String props, @RequestParam(required = false) String order) {
        if (order == null) {
            order = "asc";
        }
        List<Employee> employees=employeeService.sortEmployees(props, order);
        model.addAttribute("employee",employees);
        model.addAttribute("currentProperty",props);
        model.addAttribute("currentOrder",order);

        return "employees/list-employees";


        // localhost:8080/api/employees/sort/prop?order=desc use this endpoint for query result
        // otherwise use regular

    }
}

   /* @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
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
*/


