package cn.anselyuki.repository.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_apply_product")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class ApplyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String pid;
    private String uid;
    private Integer num;
    private Integer status;
    private Date createTime;
    private Date modifiedTime;
    private String phoneNumber;
    private String email;
    private String address;
    private String degree;
}
