package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.reward_selection_strategy.RewardStrategyType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RewardStrategyDTO {

    private int id;

    private RewardStrategyType rewardStrategyType;

    private QuizDTO quiz;

    private List<RewardStrategyParameterDTO> parameters;

    public static RewardStrategyDTO fromRewardStrategy(RewardStrategy rewardStrategy) {
        Quiz quiz1 = rewardStrategy.getQuiz();
        QuizDTO quizDTO = new QuizDTO(quiz1.getId(), quiz1.getName(), quiz1.getMaxScore(), quiz1.getDate());

        return new RewardStrategyDTO(rewardStrategy.getId(), rewardStrategy.getRewardStrategyType(),
                quizDTO, RewardStrategyParameterDTO.convertAllToDTO(rewardStrategy.getParameters()));
    }
}
