package techmaster.lesson16.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import techmaster.lesson16.demo.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
}
