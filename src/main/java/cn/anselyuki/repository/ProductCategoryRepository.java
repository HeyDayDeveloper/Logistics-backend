package cn.anselyuki.repository;

import cn.anselyuki.repository.relation.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
    ProductCategory findByName(String name);

    boolean existsByName(String name);

    List<ProductCategory> findByNameLike(String name);
}
