package techmaster.jpaemployee.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import techmaster.jpaemployee.exception.ResourceNotFoundException;
import techmaster.jpaemployee.model.Employee;
import techmaster.jpaemployee.model.SearchRequest;
import techmaster.jpaemployee.service.EmployeeService;

@Controller
@RequestMapping("/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @ExceptionHandler(ResourceNotFoundException.class)
    public String home(Model model, Exception ex) {
        List<Employee> employees = this.employeeService.findAll();
        model.addAttribute("ex", ex.getMessage());
        model.addAttribute("employees", employees);
        model.addAttribute("request", new SearchRequest());
        return "home";

    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("title", "Add New Employee");
        return "form";
    }

    @GetMapping(value = "/edit/{id}")
    public String editBookId(@PathVariable("id") int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("title", "Edit Employee");
        return "form";
    }

    @PostMapping("/save")
    public String save(@RequestParam("title") String title, @ModelAttribute @Valid Employee employee,
            BindingResult bindingResult, RedirectAttributes rAttributes, Model model, MultipartFile multipartFile)
            throws IOException {
        model.addAttribute("title", title);
        if (bindingResult.hasErrors()) {
            return "form";
        }
        this.employeeService.addOrUpdate(employee);
        rAttributes.addFlashAttribute("successMsg", "Employee duoc update thanh cong");
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable long id, RedirectAttributes rAttributes, Model model) {
        this.employeeService.deleteById(id);
        rAttributes.addFlashAttribute("successMsg", "Employee duoc delete thanh cong");
        return "redirect:/";
    }

    // @PostMapping("/search")
    // public String searchByKeyWord(@Valid @ModelAttribute("request") SearchRequest request, BindingResult result,
    //         Model model) {

    //     if (!result.hasFieldErrors()) {
    //         List<Employee> listEmployees = employeeService.searchByKeyWord(request.getKeyword());
    //         model.addAttribute("employees", listEmployees);
    //     }

    //     return "age";
    // }

    @GetMapping("/age")
    public String sea(Model model, Exception ex, @ModelAttribute("request") SearchRequest request,BindingResult result) {
        if (!result.hasFieldErrors()) {
        List<Employee> listAge = employeeService.getByAgeAbout(request.getA(), request.getB());
        List<Employee> listKeyWord = employeeService.searchByKeyWord(request.getKeyword());
        List<Employee> listE = new ArrayList<>();
        for (int i = 0; i < listAge.size(); i++) {
           if(listKeyWord.contains(listAge.get(i))){
               listE.add(listAge.get(i));
           }
        }
        model.addAttribute("ex", ex.getMessage());
        model.addAttribute("employees", listE);
        model.addAttribute("request", new SearchRequest());
        }
        return "age";
    }
}
