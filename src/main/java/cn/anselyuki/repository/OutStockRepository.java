package cn.anselyuki.repository;

import cn.anselyuki.repository.entity.OutStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutStockRepository extends JpaRepository<OutStock, String> {
}
