package majestatyczne.bestie.rewardsmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table (name = "quiz")
@Data
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int maxScore;
    @Column(nullable = false)
    private Date date;
    @OneToMany(mappedBy = "quiz")
    @JsonIgnoreProperties(value = {"quiz"}) // workaround for circular dependency
    private List<Result> results;
}
