package majestatyczne.bestie.rewardsmanager.repository;

import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardStrategyParameterRepository extends JpaRepository<RewardStrategyParameter, Integer> {
}
