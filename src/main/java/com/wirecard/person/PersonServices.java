package com.wirecard.person;

import com.wirecard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServices {

    @Autowired
    PersonRepository personRepository;

    public List<Person> findAllPerson (){
        return personRepository.findAll();
    }

    public Person findPersonById (Long id){
        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this Id: " + id));
    }

    public Person createPerson(Person person){
        return personRepository.save(person);
    }

    public Person updatePerson(Person person){
        Person entity = findPersonById(person.getId());

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void deletePerson(Long id){
        Person person = findPersonById(id);
        personRepository.delete(person);
    }

}
