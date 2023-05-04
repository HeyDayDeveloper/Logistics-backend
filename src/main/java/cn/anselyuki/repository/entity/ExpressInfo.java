package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_express_info")
@NoArgsConstructor
public class ExpressInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String address;
    private String phoneNumber;
    private Integer status;
    private Date createTime;
    private Date modifiedTime;
}
