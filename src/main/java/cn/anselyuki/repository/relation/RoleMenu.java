package cn.anselyuki.repository.relation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_role_menu")
@NoArgsConstructor
public class RoleMenu {
    @Id
    @Column(length = 36)
    private String roleId;
    @Id
    @Column(length = 36)
    private String menuId;
}
