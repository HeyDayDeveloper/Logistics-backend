package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String parentId;
    private String menuName;
    private String url;
    private String perms;
    private String icon;
    private String type;
    private Integer OrderNum;
}
