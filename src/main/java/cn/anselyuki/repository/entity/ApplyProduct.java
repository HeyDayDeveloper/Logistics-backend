package cn.anselyuki.repository.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Persistable;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_apply_product")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class ApplyProduct implements Persistable<String> {
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

    /**
     * 订单可能存在重复的信息，通过继承Persistable实现该方法后可以强制使 JPA save 时一定会执行 insert sql
     *
     * @return 固定True
     */
    @Override
    public boolean isNew() {
        return true;
    }
}
