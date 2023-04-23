package cn.anselyuki.repository;

import cn.anselyuki.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author AnselYuki
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
