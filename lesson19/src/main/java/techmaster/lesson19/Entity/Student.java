package techmaster.lesson19.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "student")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student{
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private  String  name;


//     @OneToMany(mappedBy = "studentId")
//     private List<StudentCourse> studentId = new ArrayList<>();
     @OneToMany(mappedBy = "studentId", cascade = CascadeType.ALL)
     private List<StudentCourse> students = new ArrayList<>();
}