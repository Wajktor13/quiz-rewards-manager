package majestatyczne.bestie.rewardsmanager.dao;

import majestatyczne.bestie.rewardsmanager.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findPersonByName(String name);
}
