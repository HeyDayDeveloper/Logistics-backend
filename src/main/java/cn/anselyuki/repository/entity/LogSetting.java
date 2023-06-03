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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String TableName;
    private String businessName;
    private String UrlTemplate;
    private String createUser;
    private Date createTime;
    private String deleteScript;
    private String updateScript;
}
