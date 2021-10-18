
### Cấu trúc thư mục
```
.
├── main
│   ├── java
│   │   ├── vn
│   │   │   ├── techmaster
│   │   │   │   ├── lesson7
│   │   │   │   │   ├── config
│   │   │   │   │   │   └── RepoConfig.java
│   │   │   │   │   ├── controller
│   │   │   │   │   │   └── StaffController.java
│   │   │   │   │   ├── model
│   │   │   │   │   │   └── Staff.java
│   │   │   │   │   ├── repository
│   │   │   │   │   │   ├── StaffDao.java
│   │   │   │   │   │   └── Dao.java
│   │   │   │   │   └── Lesson7Application.java
│   ├── resources
│   │   ├── static
│   │   ├── templates
│   │   │   └── allbooks.html
│   │   └── application.properties
```


### 1. Khởi tạo dự án SpringBoot
Chọn các dependencies sau đây:
1. spring-boot-starter-web
2. spring-boot-devtools
3. spring-boot-starter-thymeleaf
4. Project Lombok

### 2. Xây dựng mô hình
Tạo thư mục model
Tạo file Employee.java
```java
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @JsonIgnore  //Bỏ qua id khi nạp từ CSV
    int id;
    String firstName;
    String lastName;
    String email;
    String passportNumber;
    @JsonIgnore
    String fullName;

    public void provideFullName() {
        this.fullName = String.join(" ", this.firstName, this.lastName);
    }
    
    public boolean matchWithKeyword(String keyWord){
        String keyWordLowerCase = keyWord.toLowerCase();
        return firstName.toLowerCase().contains(keyWordLowerCase) ||
        lastName.toLowerCase().contains(keyWordLowerCase) ||
        email.toLowerCase().contains(keyWordLowerCase) ||
        fullName.toLowerCase().contains(keyWordLowerCase);
    }
}
```
### 3. Định nghĩa abstract class Dao.java

```java
public abstract class Dao<T> {
  protected  List<T> collections = new ArrayList<>();

  public abstract List<T> getAll();

  public abstract Optional<T> get(int id);
 
  public abstract void add(T t);

  public abstract void update(T t);

  public abstract void deleteByID(int id);

  public abstract void delete(T t);  
}
```

### 4. Tạo một class EmployeeDao hiện thực hoá Dao
Thêm đoạn này vào pom.xml
<dependency>
  <groupId>com.fasterxml.jackson.dataformat</groupId>
  <artifactId>jackson-dataformat-csv</artifactId>
</dependency>
```java
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
        // AtomicInteger count = new AtomicInteger();
        listEmployee.stream().forEach(emp -> {
            emp.provideFullName();
            // emp.setId(count.incrementAndGet());
        });
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
        return listEmployee;
    }

    @Override
    public Optional<Employee> get(int id) {
        // TODO Auto-generated method stub
        return listEmployee.stream().filter(u -> u.getId()==id).findFirst();
    }

    @Override
    public void add(Employee employee) {
        // TODO Auto-generated method stub
        //Cơ chế tự tăng id
        int id;
        if (listEmployee.isEmpty()) {
        id = 1;
        } else {
        Employee lastEmployee = listEmployee.get(listEmployee.size() - 1);
        id = lastEmployee.getId() + 1;      
        }
        employee.setId(id);
        listEmployee.add(employee);
        listEmployee.stream().forEach(emp -> emp.provideFullName());
    }

    @Override
    public void update(Employee employee) {
        // TODO Auto-generated method stub
        get(employee.getId()).ifPresent(existEmployee -> {
            existEmployee.setFirstName(employee.getFirstName());
            existEmployee.setLastName(employee.getLastName());
            existEmployee.setEmail(employee.getEmail());
            existEmployee.setPassportNumber(employee.getPassportNumber());
        });
    }

    @Override
    public void deleteById(int id){
        get(id).ifPresent(existEmployee -> listEmployee.remove(existEmployee));
    }

    @Override
    public List<Employee> searchByKeyword(String keyword){
        return listEmployee.stream()
        .filter(existEmployee -> existEmployee.matchWithKeyword(keyword))
        .collect(Collectors.toList());
    }

}
```
### 5. Xây dựng Config
Tạo 1 file employee.csv
Tạo thư mục Config
Tạo file RepoConfig.java
```java
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
```
### 6. Tạo EmployeeController
Tạo thư mục [controller](src/main/java/vn/techmaster/bookstore/controller), sau đó tạo [BookController.java](src/main/java/vn/techmaster/bookstore/controller/BookController.java)

```java
@Controller
@RequestMapping("/book")  //Đường dẫn /book sẽ là đường dẫn gốc chung cho các phương thức bên trong BookController
public class BookController {
  @Autowired
  private BookDao bookDao; //Gán Bean bookDao vào biến này

  @GetMapping
  public String listAll(Model model) {
    model.addAttribute("books", bookDao.getAll());
    return "allbooks";
  }  
}
```

### 7. Tạo Thymeleaf template
Trong thư mục [static/templates] tạo file [allbooks.html](src/main/resources/templates/allbooks.html)

Đoạn code này duyệt qua các đối tượng Book trong mảng để hiển thị
```html
<ul>
  <li th:each="book: ${books}">
    <a th:href="@{/book/{id}(id=${book.id})}"><strong th:text="${book.title}"></strong></a><br>
    <p th:text="${book.description}"></p>
  </li>
</ul>
```

### 9: Biên dịch và vào http://localhost:8080/book
Kết quả nhận được sẽ như sau

![](images/getAllBooks.jpg)