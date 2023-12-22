package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preference")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "reward_id")
    private Reward reward;

    @Column(nullable = false)
    private int priority;
}
