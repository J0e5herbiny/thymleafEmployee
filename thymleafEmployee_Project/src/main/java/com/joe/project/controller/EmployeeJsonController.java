package com.joe.project.controller;

import com.joe.project.dto.EmployeeDto;
import com.joe.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employees")
public class EmployeeJsonController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeJsonController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/all")
    public List<EmployeeDto> getAllEmployee(){
        return employeeService.readEmployees();
    }

    @GetMapping("/all/order")
    public List<EmployeeDto> getOrderedEmployeeList(){
        return employeeService.readOrderedEmployees();
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable("employeeId") Long id){
        return employeeService.readEmployee(id);
    }

    @PostMapping
    public void postEmployee(@RequestBody EmployeeDto employeeDto){
        employeeService.createEmployee(employeeDto);
    }

    @PutMapping(path = "/{employeeId}")
    public void putEmployee(@PathVariable("employeeId") Long id,
                            @RequestBody EmployeeDto employeeDto){
        employeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping(path = "/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Long id){
        employeeService.deleteEmployee(id);
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(){
        return "error";
    }

}
