package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class QuizDTO {

    private int id;

    private String name;

    private int maxScore;

    private Date date;
}
