package com.joe.project.repository;

import com.joe.project.entity.Employee;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepositoryTest;

    @AfterEach
    void  tearDown(){
        employeeRepositoryTest.deleteAll();
    }

    @Test
    void checkIfEmployeeExistsTest(){

        //Given
        String email= "employee@email_1.com";
        Employee employee_1= new Employee(
                "employeeFirstName_1",
                "employeeSurname_1",
                email
        );
        employeeRepositoryTest.save(employee_1);

        //When
        boolean expected= employeeRepositoryTest.findByEmail(email).isPresent();

        //Then
        assertThat(expected).isTrue();
    }

    @Test
    void checkIfEmployeeDoesNotExistTest(){

        //Given
        String email= "employee_1@email_1.com";

        //When
        boolean expected= employeeRepositoryTest.findByEmail(email).isPresent();

        //Then
        assertThat(expected).isFalse();

    }


}
