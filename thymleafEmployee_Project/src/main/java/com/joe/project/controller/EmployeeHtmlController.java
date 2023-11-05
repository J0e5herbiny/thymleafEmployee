package com.joe.project.controller;

import com.joe.project.dto.EmployeeDto;
import com.joe.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;

@Controller
@RequestMapping("employees")
public class EmployeeHtmlController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeHtmlController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.readEmployees());
        return "employees/employees-list";
    }

    @GetMapping("/all/ordered")
    public String getOrderedAllEmployees(Model model){
        model.addAttribute("employees", employeeService.readOrderedEmployees());
        return "employees/employees-list";
    }

//    @GetMapping("/{employeeId}")
    @GetMapping("/employeeId")
    public String getEmployeeById(@RequestParam("employeeId")/*@PathVariable("employeeId")*/ Long id,
                                  Model model){
        if (id == null){
            return "employees/employees-list";
        }
        else {
        EmployeeDto employeeDto = employeeService.readEmployee(id);
        model.addAttribute("employees", employeeDto);
        return (employeeDto == null)?"employees/employee-notFound":"employees/employees-list";
        }
    }

    @GetMapping("/search")
    public String searchEmployee(@RequestParam("employeeName") String name,
                                 Model model){
        List<EmployeeDto> searchEmployee = employeeService.searchEmployee(name);
//        if (searchEmployee.isEmpty()){
//            return "employees/employee-notFound";
//        }
//        else {
            model.addAttribute("employees", searchEmployee);
//            return "employees/employees-list";
//        }
        return (searchEmployee.isEmpty())?"employees/employee-notFound":"employees/employees-list";
    }

    @GetMapping("/showAddForm")
    public String showAddForm(Model model){

        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employee", employeeDto);
        return "employees/employee-form";
    }

    @PostMapping("/post")
    public String postEmployee(@ModelAttribute("employee") EmployeeDto employeeDto){
        employeeService.createEmployee(employeeDto);
        return "redirect:/employees";
    }

    @GetMapping("/showUpdateForm")
    public String showUpdateForm(@RequestParam("employeeId") int id,
                                 Model model){

        model.addAttribute("employee", employeeService.readEmployee( (long)id ));
        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int id){
        employeeService.deleteEmployee((long)id);
        return "redirect:/employees";
    }


    @ExceptionHandler(value = TemplateInputException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String templateInputExceptionHandler(){
        return "/login-authPage";
    }

}
