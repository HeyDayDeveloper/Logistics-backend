package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_In_Stock")
@NoArgsConstructor
public class InStock {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 36)
    private String pid;
    @Column(length = 36)
    private String uid;

    private Integer num;

    @Column(length = 127)
    private String address;
    @Column(length = 127)
    private String remark;

    private Date createTime;
}
