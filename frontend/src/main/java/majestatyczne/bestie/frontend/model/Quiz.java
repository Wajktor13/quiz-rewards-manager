package majestatyczne.bestie.frontend.model;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Quiz {
    private int id;
    private String name;
    private int maxScore;
    private Date date;
}

