package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Person;

import java.util.Date;

@AllArgsConstructor
public class ResultDTO {

    private int id;

    private Person person;

    private Date startDate;

    private Date endDate;

    private int score;
}
