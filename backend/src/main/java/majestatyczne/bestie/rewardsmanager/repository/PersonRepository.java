package majestatyczne.bestie.rewardsmanager.repository;

import majestatyczne.bestie.rewardsmanager.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findPersonByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM Person WHERE Person.name IN :names")
    List<Person> findAllPeopleByNames(List<String> names);
}
