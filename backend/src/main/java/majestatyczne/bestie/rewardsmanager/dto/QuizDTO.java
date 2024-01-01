package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizDTO {

    private int id;

    private String name;

    private int maxScore;

    private Date date;
}

