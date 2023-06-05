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
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 32)
    private String username;
    @Column(length = 32)
    private String operation;
    private Date time;
    @Column(length = 32)
    private String method;
    @Column(length = 127)
    private String params;
    @Column(length = 64)
    private String ip;
}
