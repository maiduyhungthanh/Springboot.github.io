package com.example.javacollection.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.javacollection.model.person;

import org.springframework.stereotype.Component;

@Component

public class serviceMethod implements servicePerson {
    private static ArrayList<person> listPerson = new ArrayList<person>();
       
    static
    {
        listPerson.add(new person("Duc", "VietNam", 38));
        listPerson.add(new person("Chinh", "VietNam", 26));
        listPerson.add(new person("Tung", "VietNam", 24));
        listPerson.add(new person("Vu", "Lao", 31));
        listPerson.add(new person("Hoa", "Lao", 27));
        listPerson.add(new person("Son", "TrungQuoc", 31));
        listPerson.add(new person("Khiem", "TrungQuoc", 26));
    }

    @Override
    public List<person> list() {
           return listPerson;
    }

    @Override
    public List<person> list20to30() {
        List <person> age20to30 = new ArrayList<>(); 
        for (person p : listPerson) {
           if(p.getAge()>=20 && p.getAge()<=30){
               age20to30.add(p);
           }
        }
           return age20to30;
    }

    @Override
    public Double ageTB() {
           Double total = (double) 0 ;
        for (person s : listPerson) {
            total += s.getAge();
        }
          Double result = total/listPerson.size();
           return result;
    }

    @Override
    public Map<String,Double> ageTBofNationality() {
           // TODO Auto-generated method stub
            Map<String, Double> result = listPerson.stream()
            .collect(Collectors.groupingBy(person::getNationality,Collectors.averagingInt(person::getAge)));
           return result;
    }
}
