package majestatyczne.bestie.rewardsmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.reward_selection_strategy.RewardStrategyType;

import java.util.List;

public record RewardStrategyDTO (

    int id,

    RewardStrategyType rewardStrategyType,

    @JsonProperty("quiz")
    QuizDTO quizDTO,

    @JsonProperty("parameters")
    List<RewardStrategyParameterDTO> rewardStrategyParameterDTOs
) {

    public static RewardStrategyDTO convertToDTO(RewardStrategy rewardStrategy) {
        Quiz quiz1 = rewardStrategy.getQuiz();
        QuizDTO quizDTO = new QuizDTO(quiz1.getId(), quiz1.getName(), quiz1.getMaxScore(), quiz1.getDate());

        return new RewardStrategyDTO(rewardStrategy.getId(), rewardStrategy.getRewardStrategyType(),
                quizDTO, RewardStrategyParameterDTO.convertAllToDTO(rewardStrategy.getParameters()));
    }
}
