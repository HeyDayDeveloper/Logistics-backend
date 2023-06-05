package cn.anselyuki.repository.entity.algorithm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_algorithm_log")
@NoArgsConstructor
public class algorithmLog {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 32)
    private String applyName;
    @Column(length = 36)
    private String distributionId;
    @Column(length = 127)
    private String remark;
}
