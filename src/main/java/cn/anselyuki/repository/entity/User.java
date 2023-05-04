package cn.anselyuki.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author AnselYuki
 */
@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer status;
    private Date createTime;
    private Date modifiedTime;
    private Boolean sex;
    private Integer type;
    private Date birth;
    private String avatar;

    /**
     * 通过泛型构造函数，将DTO转换为Entity,DTO中的属性必须与Entity中的属性一致
     *
     * @param user DTO
     * @param <T>  DTO类型
     */
    public <T> User(T user) {
        BeanUtils.copyProperties(user, this);
    }
}
