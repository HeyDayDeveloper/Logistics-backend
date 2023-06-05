package cn.anselyuki.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Boolean sex;
    private Integer type;
    private Integer status;

    @Column(unique = true, length = 16)
    @NotBlank(message = "用户名不能为空")
    private String username;
    @JsonIgnore
    @Column(length = 127)
    private String password;
    @Column(length = 16)
    private String nickname;
    @Column(length = 32)
    private String email;
    @Column(length = 32)
    private String phoneNumber;
    @Column(length = 127)
    private String avatar;

    private Date birth;
    private Date createTime;
    private Date modifiedTime;

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
