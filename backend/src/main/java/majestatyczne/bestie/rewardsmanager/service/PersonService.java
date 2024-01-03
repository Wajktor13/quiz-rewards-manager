package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
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

    @Transactional
    public void addPerson(Person person) {
        if (findPersonByName(person.getName()).isEmpty()) {
            personRepository.save(person);
        }
    }

    public Optional<Person> findPersonByName(String name) {
        return Optional.ofNullable(personRepository.findPersonByName(name));
    }

    public List<Person> findAllPeopleByName(List<String> names) {
        return personRepository.findAllPeopleByNames(names);
    }

    @Transactional
    public void addPeopleWithoutDuplicates(List<Person> people) {
        List<String> names = people
                .stream()
                .map(Person::getName)
                .toList();

        List<Person> alreadyAddedPeople = findAllPeopleByName(names);

        List<Person> newPeople = people
                .stream()
                .filter(person -> alreadyAddedPeople
                        .stream()
                        .noneMatch(alreadyAddedPerson -> alreadyAddedPerson.getName().equals(person.getName())))
                .toList();

        personRepository.saveAll(newPeople);
    }

    public void updatePersonInResults(Result result) {
        Optional<Person> person = findPersonByName(result.getPerson().getName());
        person.ifPresent(result::setPerson);
    }
}
