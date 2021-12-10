package techmaster.lesson19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import techmaster.lesson19.Entity.StudentCourse;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse,Long> {
    
}