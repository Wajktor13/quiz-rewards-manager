package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.repository.PersonRepository;
import majestatyczne.bestie.rewardsmanager.model.Person;
import majestatyczne.bestie.rewardsmanager.model.Result;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void addPeople(List<Person> people) {
        List<Person> newPeople = people
                .stream()
                .filter(person -> findPersonByName(person.getName()).isEmpty())
                .toList();
        personRepository.saveAll(newPeople);
    }

    public void updatePersonInResults(Result result) {
        Optional<Person> person = findPersonByName(result.getPerson().getName());
        person.ifPresent(result::setPerson);
    }
}
