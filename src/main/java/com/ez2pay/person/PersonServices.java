package com.ez2pay.person;

import com.ez2pay.util.Utils;
import com.ez2pay.exception.ResourceNotFoundException;
import com.ez2pay.util.DozerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServices {

    private static Logger logger = LoggerFactory.getLogger(PersonServices.class);

    @Autowired
    PersonRepository personRepository;

    public List<PersonDTO> findAllPerson (){
        return DozerConverter.parseObjectList(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO findPersonById (Long id){
        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this Id: " + id));
        return DozerConverter.parseObject(entity, PersonDTO.class);
    }

    public PersonDTO createPerson(PersonDTO personDTO){
        logger.debug("Original - PersonDTO: " + Utils.parseObjectToJson(personDTO));

        var entity = DozerConverter.parseObject(personDTO, Person.class);
        logger.debug("Destination - Person: " + Utils.parseObjectToJson(entity));

        entity = personRepository.save(entity);
        return DozerConverter.parseObject(entity, PersonDTO.class);
    }

    public PersonDTO updatePerson(PersonDTO personDTO){
        var entity = personRepository.findById(personDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this Id: " + personDTO.getId()));

        entity.setFirstName(personDTO.getPersonFirstName());
        entity.setLastName(personDTO.getPersonLastName());
        entity.setAddress(personDTO.getPersonAddress());
        entity.setGender(personDTO.getPersonGender());

        entity = personRepository.save(entity);
        return DozerConverter.parseObject(entity, PersonDTO.class);
    }

    public void deletePerson(Long id){
        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this Id: " + id));
        personRepository.delete(entity);
    }

}
