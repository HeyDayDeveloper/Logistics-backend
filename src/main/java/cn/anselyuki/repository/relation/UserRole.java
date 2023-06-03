package cn.anselyuki.repository.relation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_user_role")
@NoArgsConstructor
public class UserRole {
    @Id
    private String userId;
    @Id
    private String roleId;
}
