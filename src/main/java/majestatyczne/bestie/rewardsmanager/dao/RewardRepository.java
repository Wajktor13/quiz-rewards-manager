package majestatyczne.bestie.rewardsmanager.dao;

import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Integer> {
}
