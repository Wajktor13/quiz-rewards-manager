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

    public Optional<Person> findByName(String name) {
        return Optional.ofNullable(personRepository.findPersonByName(name));
    }

    public List<Person> findAllByNames(List<String> names) {
        return personRepository.findAllPeopleByNames(names);
    }

    @Transactional
    public void addAllWithoutDuplicates(List<Person> people) {
        List<String> names = people
                .stream()
                .map(Person::getName)
                .toList();

        List<Person> alreadyAddedPeople = findAllByNames(names);

        List<Person> newPeople = people
                .stream()
                .filter(person -> alreadyAddedPeople
                        .stream()
                        .noneMatch(alreadyAddedPerson -> alreadyAddedPerson.getName().equals(person.getName())))
                .toList();

        personRepository.saveAll(newPeople);
    }

    public void updatePersonInResults(Result result) {
        Optional<Person> person = findByName(result.getPerson().getName());
        person.ifPresent(result::setPerson);
    }
}
