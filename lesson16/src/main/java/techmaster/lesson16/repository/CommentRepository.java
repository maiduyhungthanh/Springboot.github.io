package techmaster.lesson16.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import techmaster.lesson16.demo.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    
}