package com.joe.project.service;

import com.joe.project.dto.EmployeeDto;
import com.joe.project.entity.Employee;
import com.joe.project.exception.BadRequestException;
import com.joe.project.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeServiceTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        employeeServiceTest= new EmployeeService(employeeRepository);
    }

    @Test
    void canGetAllEmployees(){

        //Given
        //When
        employeeServiceTest.readEmployees();
        //Then
        verify(employeeRepository).findAll();

    }

    @Test
    void canAddEmployee(){

        //Given
//        Employee employee= new Employee(
//                "employeeFirstName",
//                "employeeLastName",
//                "employee@email"
//        );
        EmployeeDto employeeDto= new EmployeeDto(
                "employeeFirstName",
                "employeeLastName",
                "employee@email"
        );
        //When
        employeeServiceTest.createEmployee(employeeDto);
        //Then
        ArgumentCaptor<EmployeeDto> employeeDtoArgumentCaptor=
                ArgumentCaptor.forClass(employeeDto.getClass());
//        verify(employeeRepository).save(employeeDtoArgumentCaptor.capture());
        EmployeeDto captorEmployee= employeeDtoArgumentCaptor.getValue();
        assertThat(captorEmployee).isEqualTo(employeeDto);
    }

    @Test
    void throwWhenEmailIsTaken(){
        //Given
        EmployeeDto employeeDto= new EmployeeDto(
                "employeeFirstName",
                "employeeLastName",
                "employee@email"
        );
        given(employeeRepository.findByEmail(employeeDto.getEmail()).isPresent())
                .willReturn(true);
        //When
        assertThatThrownBy( ()-> employeeServiceTest.createEmployee(employeeDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email "+employeeDto.getEmail()+" is taken!");
        //Then
    }

    @Test
    void canDeleteEmployee(){
        //Given
        EmployeeDto employeeDto= new EmployeeDto(
                "employeeFirstName",
                "employeeLastName",
                "employee@email"
        );
        employeeDto.setId(1L);
        employeeServiceTest.createEmployee(employeeDto);
        //When
        employeeServiceTest.deleteEmployee(employeeDto.getId());
        //Then
        ArgumentCaptor<EmployeeDto> employeeDtoArgumentCaptor=
                ArgumentCaptor.forClass(employeeDto.getClass());
        Long id= employeeDtoArgumentCaptor.capture().getId();
        verify(employeeRepository).deleteById(id);
    }

}
