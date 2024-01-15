package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int correctAnswers;

    @Column(nullable = false)
    private int allAnswers;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
}
