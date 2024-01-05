package majestatyczne.bestie.rewardsmanager.reward_selection_strategy;

import majestatyczne.bestie.rewardsmanager.model.Preference;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import majestatyczne.bestie.rewardsmanager.model.Result;
import majestatyczne.bestie.rewardsmanager.service.RewardCategoryService;

import java.util.List;

public interface RewardSelectionStrategy {
    public List<Result> insertRewards(Quiz quiz, List<Preference> preferences);
}
