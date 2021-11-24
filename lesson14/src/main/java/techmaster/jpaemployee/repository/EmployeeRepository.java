package techmaster.jpaemployee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import techmaster.jpaemployee.model.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee,Long> {
    
}
