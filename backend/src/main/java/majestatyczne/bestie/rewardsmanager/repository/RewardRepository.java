package majestatyczne.bestie.rewardsmanager.repository;

import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Integer> {

    Reward findRewardByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM Reward WHERE Reward.name IN :names")
    List<Reward> findAllRewardsByNames(List<String> names);
}
