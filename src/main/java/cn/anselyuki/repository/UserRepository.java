package cn.anselyuki.repository;

import cn.anselyuki.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author AnselYuki
 * @date 2023/4/23 17:28
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
