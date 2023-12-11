package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reward")
@Data
@NoArgsConstructor
public class Reward {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "rewardCategory_id")
    private RewardCategory rewardCategory;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
}
