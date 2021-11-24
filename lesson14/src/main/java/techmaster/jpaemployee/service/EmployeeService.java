package techmaster.jpaemployee.service;

import java.util.List;

import techmaster.jpaemployee.model.Employee;



public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(long id);

    void addOrUpdate(Employee employee);

    void deleteById(long id);

    List<Employee> searchByKeyWord(String keyword);

    // public  void addEmployee(Employee e);

    // public Employee updateByID(long id , Employee e);
    public List<Employee> getByAgeAbout(int start, int end) ;
}
