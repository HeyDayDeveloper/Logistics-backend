package cn.anselyuki.repository;

import cn.anselyuki.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByName(String name);

    Product findByName(String name);

    List<Product> findByNameLike(String name);
}
