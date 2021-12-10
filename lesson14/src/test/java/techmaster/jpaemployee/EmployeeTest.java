package techmaster.jpaemployee;

import java.util.List;

import techmaster.jpaemployee.model.Employee;
import techmaster.jpaemployee.service.EmployeeService;
import techmaster.jpaemployee.service.EmployeeServiceImp;

public class EmployeeTest {
    public static void main(String[] args) {
        EmployeeServiceImp dao = new EmployeeServiceImp();
        List<Employee> list = dao.findAll();
        for (Employee user : list) {
            System.out.println(user);
        }
    }
}
