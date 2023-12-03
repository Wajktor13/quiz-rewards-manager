package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.ResultRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
}
