package techmaster.lesson19.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "student_course")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCourse {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "score")
    private Integer score;
//(fetch = FetchType.LAZY)  
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student studentId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course courseId;
}
