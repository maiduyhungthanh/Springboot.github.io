package techmaster.lesson16.service;

import java.util.List;

import org.springframework.stereotype.Service;

import techmaster.lesson16.demo.User;

@Service
public interface Dao {
    public List<User> findAll();
}
