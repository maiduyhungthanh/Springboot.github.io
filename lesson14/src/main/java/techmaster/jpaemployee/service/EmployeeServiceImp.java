package techmaster.jpaemployee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import techmaster.jpaemployee.exception.ResourceNotFoundException;
import techmaster.jpaemployee.model.Employee;
import techmaster.jpaemployee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImp implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    // public List<Employee> emList = new ArrayList<>();
    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id không tồn tại"));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void addOrUpdate(Employee employee) {
        if (employee.getId() != 0) {
            this.findById(employee.getId());
        }
        this.employeeRepository.save(employee);
    }

    @Override
    public void deleteById(long id) {
        Employee employee = this.findById(id);
        this.employeeRepository.delete(employee);

    }

    @Override
    public List<Employee> searchByKeyWord(String keyword) {
        return findAll().stream().filter(employee -> employee.matchWithKeyWord(keyword)).collect(Collectors.toList());
    }

    @Override
    public List<Employee> getByAgeAbout(int start, int end) {
        return employeeRepository.findAll().stream().filter(e -> e.getAge() >= start && e.getAge() <= end).collect(Collectors.toList());
      }
}
