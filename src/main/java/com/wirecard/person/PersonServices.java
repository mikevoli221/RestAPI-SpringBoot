package com.wirecard.person;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.wirecard.exception.ResourceNotFoundException;
import com.wirecard.util.DozerConverter;
import com.wirecard.util.Utils;
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
        var dto = findPersonById(personDTO.getId());
        var entity = DozerConverter.parseObject(dto, Person.class);
        entity = personRepository.save(entity);
        return DozerConverter.parseObject(entity, PersonDTO.class);
    }

    public void deletePerson(Long id){
        var dto = findPersonById(id);
        var entity = DozerConverter.parseObject(dto, Person.class);
        personRepository.delete(entity);
    }

}
