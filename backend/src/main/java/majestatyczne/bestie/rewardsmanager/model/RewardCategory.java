package majestatyczne.bestie.rewardsmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "reward_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "rewards")
public class RewardCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_category_id")
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "rewardCategory")
    private List<Reward> rewards;
}
