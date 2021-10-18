package vn.techmaster.lesson7.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vn.techmaster.lesson7.Repository.EmployeeDao;

@Configuration
public class RepoConfig {
    @Bean
    public EmployeeDao employeeDao(){
        return new EmployeeDao("employee.csv");
    }
}
