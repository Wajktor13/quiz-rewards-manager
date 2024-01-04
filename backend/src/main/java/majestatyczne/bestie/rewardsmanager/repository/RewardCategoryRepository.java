package majestatyczne.bestie.rewardsmanager.repository;

import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardCategoryRepository extends JpaRepository<RewardCategory, Integer> {

    RewardCategory findRewardCategoryByName(String name);
}
