package cn.anselyuki.repository.entity.algorithm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_algorithm_history")
@NoArgsConstructor
public class algorithmHistory {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 36)
    private String distributionID;
    @Column(length = 127)
    private String inputJson;
    @Column(length = 127)
    private String outputJson;
    private Date createTime;
}
