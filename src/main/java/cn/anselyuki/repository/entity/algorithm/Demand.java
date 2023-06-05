package cn.anselyuki.repository.entity.algorithm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_demand")
@NoArgsConstructor
public class Demand {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double distance;
    private Integer peopleNum;
    private Float applyNum;
}
