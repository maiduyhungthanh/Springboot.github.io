package vn.techmaster.lesson7.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.util.ResourceUtils;

import vn.techmaster.lesson7.model.Employee;


public class EmployeeDao extends Dao<Employee> {

    public EmployeeDao(String csvFile){
        this.readCSV(csvFile);
    }

    @Override
    public void readCSV(String csvFile) {
        // TODO Auto-generated method stub
        try {
            File file = ResourceUtils.getFile("classpath:static/" + csvFile);
            CsvMapper mapper = new CsvMapper(); // Dùng để ánh xạ cột trong CSV với từng trường trong POJO
            CsvSchema schema = CsvSchema.emptySchema().withHeader(); // Dòng đầu tiên sử dụng làm Header
            ObjectReader oReader = mapper.readerFor(Employee.class).with(schema); // Cấu hình bộ đọc CSV phù hợp với kiểu
            Reader reader = new FileReader(file);
            MappingIterator<Employee> mi = oReader.readValues(reader); // Iterator đọc từng dòng trong file
            while (mi.hasNext()) {
              Employee employee = mi.next();
              this.add(employee);
            }
          } catch (IOException e) {
            System.out.println(e);   
          }
    }

    @Override
    public List<Employee> getAll() {
        // TODO Auto-generated method stub
        return myList;
    }

    @Override
    public Optional<Employee> get(int id) {
        // TODO Auto-generated method stub
        return myList.stream().filter(u -> u.getId()==id).findFirst();
    }

    @Override
    public void add(Employee employee) {
        // TODO Auto-generated method stub
        //Cơ chế tự tăng id
        int id;
        if (myList.isEmpty()) {
        id = 1;
        } else {
        Employee lastEmployee = myList.get(myList.size() - 1);
        id = lastEmployee.getId() + 1;      
        }
        employee.setId(id);
        myList.add(employee);
        myList.stream().forEach(emp -> emp.provideFullName());
    }

    @Override
    public void update(Employee employee) {
        // TODO Auto-generated method stub
        get(employee.getId()).ifPresent(existEmployee -> {
            existEmployee.setFirstName(employee.getFirstName());
            existEmployee.setLastName(employee.getLastName());
            existEmployee.setEmailId(employee.getEmailId());
            existEmployee.setPassportNumber(employee.getPassportNumber());
        });
    }

    @Override
    public void deleteById(int id){
        get(id).ifPresent(existEmployee -> myList.remove(existEmployee));
    }

    @Override
    public List<Employee> searchByKeyword(String keyword){
        return myList.stream()
        .filter(existEmployee -> existEmployee.matchWithKeyword(keyword))
        .collect(Collectors.toList());
    }

}
