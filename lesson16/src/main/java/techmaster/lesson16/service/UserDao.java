package techmaster.lesson16.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import techmaster.lesson16.demo.User;
import techmaster.lesson16.exception.ResourceNotFoundException;
import techmaster.lesson16.repository.UserRepository;



@Component
public class UserDao implements Dao {
    @Autowired
    private UserRepository usRepo;
   
    public List<User> findAll() {
        return usRepo.findAll();
    }

    // public User findById(long id) {
    //     return usRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("id không tồn tại"));
    // }

   
    // public void addOrUpdate(User us) {
    //     if (us.getId() != 0) {
    //         this.findById(us.getId());
    //     }
    //     this.usRepo.save(us);
    // }

   
    // public void deleteById(long id) {
    //     User us = this.findById(id);
    //     this.usRepo.delete(us);

    // }

   
    // public List<User> searchByKeyWord(String keyword) {
    //     return getAllUser().stream().filter(employee -> employee.matchWithKeyWord(keyword)).collect(Collectors.toList());
    // }

    
}
