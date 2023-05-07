package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_Out_Stock")
@NoArgsConstructor
public class OutStock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String pid;
    private String uid;
    private String address;
    private Integer num;
    private String remark;
    private Date createTime;
}
