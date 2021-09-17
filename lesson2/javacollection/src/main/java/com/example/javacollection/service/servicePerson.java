package com.example.javacollection.service;

import java.util.List;
import java.util.Map;

import com.example.javacollection.model.person;

public interface servicePerson {
    public List<person> list();
    public List<person> list20to30();
    public Double ageTB();
    public Map <String, Double> ageTBofNationality();
}
