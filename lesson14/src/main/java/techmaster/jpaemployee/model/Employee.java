package techmaster.jpaemployee.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String photo;

    @Column
    @NotEmpty(message = "Không được bỏ trống trường này")
    private String firstName;
    @Column
    @NotEmpty(message = "Không được bỏ trống trường này")
    private String lastName;

    

    @Column
    @Email(message = "Email có định dạng không chính xác")
    @NotEmpty(message = "Không được bỏ trống trường này")
    private String email;

    @Column
    private String gender;

    @Column
    private String address;

    @Column
    private Long salary;

    @Column(name = "BIRTH_DAY")
    private Date bDay;

    public int getAge() {
        Date birthDate = new Date(bDay.getTime());
        long millis=System.currentTimeMillis(); 
        java.sql.Date date=new java.sql.Date(millis);
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");                           
        int d1 = Integer.parseInt(formatter.format(birthDate));                            
        int d2 = Integer.parseInt(formatter.format(date));                          
        int age = (d2 - d1) / 10000;                                                       
        return age;
    }

    public boolean matchWithKeyWord(String keyword) {
        String fullName = firstName.concat(" ").concat(lastName).toLowerCase();
        String lowerKeyWord = keyword.toLowerCase();
        return fullName.contains(lowerKeyWord);
    }

    public String getImage(){
        return "../photos/" + this.photo;
    }
}
