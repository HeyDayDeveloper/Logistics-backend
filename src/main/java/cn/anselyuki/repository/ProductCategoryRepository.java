package cn.anselyuki.repository;

import cn.anselyuki.repository.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
    ProductCategory findByName(String name);

    boolean existsByName(String name);
}
