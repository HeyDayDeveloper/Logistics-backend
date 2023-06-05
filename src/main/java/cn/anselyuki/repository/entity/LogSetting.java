package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_log_setting")
@NoArgsConstructor
public class LogSetting {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String TableName;
    @Column(length = 32)
    private String businessName;
    @Column(length = 127)
    private String UrlTemplate;
    @Column(length = 32)
    private String createUser;
    private Date createTime;
    @Column(length = 512)
    private String deleteScript;
    @Column(length = 512)
    private String updateScript;
}
