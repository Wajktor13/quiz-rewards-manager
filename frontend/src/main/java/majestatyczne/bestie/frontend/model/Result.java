package majestatyczne.bestie.frontend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Result {
    private int id;
    private Person person;
    private Date startDate;
    private Date endDate;
    private int score;

}

