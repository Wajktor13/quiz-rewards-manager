package majestatyczne.bestie.rewardsmanager.dto;

import majestatyczne.bestie.rewardsmanager.model.Quiz;

import java.util.Date;

public record QuizDTO (

        int id,

        String name,

        int maxScore,

        Date date
) {

    public static QuizDTO convertToDTO(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        return new QuizDTO(quiz.getId(), quiz.getName(), quiz.getMaxScore(), quiz.getDate());
    }
}
