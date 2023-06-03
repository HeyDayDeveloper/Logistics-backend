package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_log")
@NoArgsConstructor
public class LogStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String operation;
    private Date time;
    private String method;
    private String params;
    private String ip;
}
