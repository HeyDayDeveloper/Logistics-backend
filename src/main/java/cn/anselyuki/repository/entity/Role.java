package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_role")
@NoArgsConstructor
public class Role {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 32)
    private String roleName;
    @Column(length = 127)
    private String remark;
    private Date createTime;
    private Date modifiedTime;
    private Integer status;
}
