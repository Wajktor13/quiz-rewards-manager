package majestatyczne.bestie.rewardsmanager.repository;

import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardStrategyRepository extends JpaRepository<RewardStrategy, Integer> {

    Optional <RewardStrategy> findByQuizId(int quizId);
}
