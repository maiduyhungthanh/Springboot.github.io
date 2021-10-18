package vn.techmaster.lesson7.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.lesson7.Repository.EmployeeDao;
import vn.techmaster.lesson7.Request.DeleteRequest;
import vn.techmaster.lesson7.Request.SearchRequest;
import vn.techmaster.lesson7.model.Employee;



@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeDao employeeDao;

    @GetMapping(value = {"/", ""})
    public String listAll(Model model){
        model.addAttribute("employees", employeeDao.getAll());
        return "home";
    }

    @GetMapping(value = {"/{id}"})
    public String getById(@PathVariable("id") int id, Model model){
        Optional<Employee> employee = employeeDao.get(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
        }
        return "employee";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("employee", new Employee());
        return "add";
    }

    @PostMapping("/save")
    public String save(Employee employee, BindingResult result){
        if (result.hasErrors()) {
            return "add";
        }
        if (employee.getId() > 0) { 
            employeeDao.update(employee);
          } else {
            employeeDao.add(employee);
          }
             
        return "redirect:/employee"; 
    }

    @GetMapping("/edit/{id}")
    public String editEmployeeById(@PathVariable("id") int id, Model model){
        Optional<Employee> employee = employeeDao.get(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
        }
        return "add";
    }

    @PostMapping("/delete")
    public String deleteById(@ModelAttribute DeleteRequest request, BindingResult result ){
       if (!result.hasErrors()) {
            employeeDao.deleteById(request.getId());
       }
       return "redirect:/employee";
    }

    @GetMapping("/search")
    public String searchForm(Model model){
        model.addAttribute("searchrequest", new SearchRequest());
        return "search";
    }

    @PostMapping("/search")
    public String searchByKeyword(@ModelAttribute SearchRequest request, BindingResult result, Model model){
        if (!result.hasErrors()) {
            model.addAttribute("employees", employeeDao.searchByKeyword(request.getKeyWord()));
        }
        return "home";
    }
}
