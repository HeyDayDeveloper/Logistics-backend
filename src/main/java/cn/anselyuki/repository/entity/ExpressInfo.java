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
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 32)
    private String phoneNumber;
    @Column(length = 32)
    private String productName;
    @Column(length = 16)
    private String userName;

    private Integer status;

    private Date createTime;
    private Date modifiedTime;

    @Column(length = 127)
    private String sendAddress;
    @Column(length = 127)
    private String address;
}
