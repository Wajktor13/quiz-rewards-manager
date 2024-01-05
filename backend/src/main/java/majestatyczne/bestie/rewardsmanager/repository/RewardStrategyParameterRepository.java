package majestatyczne.bestie.rewardsmanager.repository;

import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardStrategyParameterRepository extends JpaRepository<RewardStrategyParameter, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM reward_strategy_parameter WHERE reward_strategy_id IN :strategyIds")
    void deleteAllByRewardStrategyIds(List<Integer> strategyIds);
}
