package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_apply_product")
@NoArgsConstructor
public class ApplyProduct {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 36)
    private String pid;
    @Column(length = 36)
    private String uid;

    private Integer num;
    private Integer status;
    private Integer degree;

    private Date createTime;
    private Date modifiedTime;

    @Column(length = 32)
    private String productName;
    @Column(length = 32)
    private String phoneNumber;
    @Column(length = 32)
    private String email;
    @Column(length = 127)
    private String address;
    @Column(length = 127)
    private String sendAddress;
}
