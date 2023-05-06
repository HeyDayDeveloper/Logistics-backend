package cn.anselyuki.repository;

import cn.anselyuki.repository.entity.ApplyProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyProductRepository extends JpaRepository<ApplyProduct, String> {
    List<ApplyProduct> findByOrderByDegreeDesc();
}
