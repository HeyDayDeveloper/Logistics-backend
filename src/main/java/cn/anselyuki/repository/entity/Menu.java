package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_menu")
@NoArgsConstructor
public class Menu {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 36)
    private String parentId;
    @Column(length = 32)
    private String menuName;
    @Column(length = 32)
    private String url;
    @Column(length = 127)
    private String perms;
    @Column(length = 127)
    private String icon;
    @Column(length = 36)
    private String typeId;
    private Integer OrderNum;
}
