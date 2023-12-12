package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.PersonRepository;
import majestatyczne.bestie.rewardsmanager.model.Person;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public void addPerson(Person person) {
        if (findPersonByName(person.getName()).isEmpty()) {
            personRepository.save(person);
        }
    }

    public Optional<Person> findPersonByName(String name) {
        return Optional.ofNullable(personRepository.findPersonByName(name));
    }
}
