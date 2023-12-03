package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "rewardCategory")
@Data
@NoArgsConstructor
public class RewardCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rewardCategory_id")
    private int id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private int priority;
    @OneToMany(mappedBy = "rewardCategory")
    private List<Reward> rewards;
}
