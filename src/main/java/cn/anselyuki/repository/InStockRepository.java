package cn.anselyuki.repository;

import cn.anselyuki.repository.entity.InStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InStockRepository extends JpaRepository<InStock, String> {
}
