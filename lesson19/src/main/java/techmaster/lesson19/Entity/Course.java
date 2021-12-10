package techmaster.lesson19.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "course")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course{
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private  String  name;

    // @OneToMany(mappedBy = "courseId")
    // private List<StudentCourse> courseId = new ArrayList<>();
    @OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL)
    private List<StudentCourse> courses = new ArrayList<>();
}
