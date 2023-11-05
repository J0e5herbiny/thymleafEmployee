package com.joe.project.service;


import com.joe.project.dto.EmployeeDto;
import com.joe.project.entity.Employee;
import com.joe.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void createEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getSurName(),
                employeeDto.getEmail()
        );
        employeeRepository.save(employee);
    }



    public List<EmployeeDto> readEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for ( Employee employee : employees ) {
            employeeDtos.add(employee.employeeDto());
        }
        return employeeDtos;
    }

    public List<EmployeeDto> readOrderedEmployees(){
        List<Employee> byNameAscOrder =
                employeeRepository.findAllByOrderByFirstNameAsc();
        List<EmployeeDto> byNameAscOrderDto = new ArrayList<>();
        for (Employee employee : byNameAscOrder ) {
            byNameAscOrderDto.add(employee.employeeDto());
        }
        return byNameAscOrderDto;
    }

    public EmployeeDto readEmployee(Long id){
        Optional<Employee> employeeById =
                employeeRepository.findById(id);
        if ( !employeeById.isPresent()){
//            throw new IllegalStateException("Employee with id:"+id+" doesn't exist !");
            return null;
        }else {
            return employeeById.get().employeeDto();
        }


    }

//    @Transactional
    public void updateEmployee(Long id, EmployeeDto employeeDto){
        Employee employee = employeeRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalStateException("This employee doesn't exist !"));

        if (employeeDto.getFirstName() != null && Objects.equals(employee.getFirstName(), employeeDto.getFirstName())){
            employee.employeeDto().setFirstName(employeeDto.getFirstName());
        }
        if (employeeDto.getSurName() != null && Objects.equals(employee.getSurName(), employeeDto.getSurName())){
            employee.employeeDto().setSurName(employeeDto.getSurName());
        }
        if (employeeDto.getEmail() != null && Objects.equals(employee.getEmail(), employeeDto.getEmail())){
            Optional<Employee> employeeByEmail = employeeRepository.findByEmail(employeeDto.getEmail());
            if (employeeByEmail.isPresent()){
                throw new IllegalStateException("Email is taken !");
            }
            employee.employeeDto().setEmail(employeeDto.getEmail());
        }

    }


    public void deleteEmployee(Long id){
        if (!employeeRepository.existsById(id)){
            throw new IllegalStateException("Employee not found !");
        }
        employeeRepository.deleteById(id);
    }

    public List<EmployeeDto> searchEmployee(String name){

        List<EmployeeDto> searchResult = new ArrayList<>();

        if (name != null){
            List<Employee> employees = employeeRepository.findByFirstNameContainsOrSurNameContainsAllIgnoreCase(name, name);
            for ( Employee employee : employees) {
                searchResult.add(employee.employeeDto());
            }
        }

        return searchResult;
    }

}
