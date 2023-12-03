package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.PersonRepository;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
}
