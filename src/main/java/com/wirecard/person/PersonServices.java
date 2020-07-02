package com.wirecard.person;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    public Person findPersonById (String id){
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Minh Hiep");
        person.setLastName("Ho");
        person.setAddress("HCMC, Vietnam");
        person.setGender("Male");
        return person;
    }

    public List<Person> findAllPerson (){
        List<Person> list = new ArrayList<Person>();
        for (int i = 0; i <= 10; i++) {
            Person person = new Person();
            person.setId(counter.incrementAndGet());
            person.setFirstName("Minh Hiep");
            person.setLastName("Ho");
            person.setAddress("HCMC, Vietnam");
            person.setGender("Male");
            list.add(person);
        }
        return list;
    }
}
