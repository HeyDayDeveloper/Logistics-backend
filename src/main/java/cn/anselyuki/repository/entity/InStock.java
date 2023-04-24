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
@Table(schema = "logisticSystem", name = "tb_In_Stock")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class InStock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    // 产品ID
    private String pid;
    // 操作管理员ID
    private String uid;
    // 捐赠人ID
    private String donationId;
    private String num;
    private String remark;
    private Date createTime;
}
