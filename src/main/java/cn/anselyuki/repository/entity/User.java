package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author AnselYuki
 * @date 2023/4/23 17:11
 */
@Data
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer status;
    private Date createTime;
    private Date modifiedTime;
    private Integer sex;
    private String salt;
    private Integer type;
    private String password;
    private Date birth;
    private Long departmentId;
    private String avatar;
}
