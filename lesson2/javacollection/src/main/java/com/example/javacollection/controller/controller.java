package com.example.javacollection.controller;

import java.util.List;
import java.util.Map;

import com.example.javacollection.model.person;
import com.example.javacollection.service.servicePerson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class controller {
    @Autowired
    private servicePerson sPerson;
    @GetMapping("/list")
    public ResponseEntity <?> getList(){
        List<person> persons = sPerson.list();
        return ResponseEntity.ok(persons);
    }
    @GetMapping("/list20to30")
    public ResponseEntity <?> getList20to30(){
        List<person> persons = sPerson.list20to30();
        return ResponseEntity.ok(persons);
    }
    @GetMapping("/agetb")
    public ResponseEntity <?> getAgeTB(){
        Double ageTB = sPerson.ageTB();
        return ResponseEntity.ok(ageTB);
    }
    @GetMapping("/agetbofnationatily")
    public ResponseEntity <?> getAgeTBofNationatily(){
        Map<String,Double> ageTB = sPerson.ageTBofNationality();
        return ResponseEntity.ok(ageTB);
    }
}
