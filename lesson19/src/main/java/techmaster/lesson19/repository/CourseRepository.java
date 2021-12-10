package techmaster.lesson19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import techmaster.lesson19.Entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    
}
