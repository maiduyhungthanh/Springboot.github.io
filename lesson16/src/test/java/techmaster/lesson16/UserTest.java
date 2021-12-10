package techmaster.lesson16;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import techmaster.lesson16.demo.User;
import techmaster.lesson16.repository.UserRepository;
import techmaster.lesson16.service.Dao;
import techmaster.lesson16.service.UserDao;

@DataJpaTest
public class UserTest implements Dao{
   @Autowired
   private UserDao dao;
   
   @Autowired
    private UserRepository usRepo;

   
   @Override
   public List<User> findAll(){
   //      List<User> list = dao.getAllUser();       
   //      String name = "thanh";
   //      assertEquals(list.get(0).getName(), name);
   return usRepo.findAll();
   }

   @Test
   public void getAll(){
              List<User> list = dao.findAll();       
        String name = "thanh";
        assertEquals(list.get(0).getName(), name);
   }
   
}