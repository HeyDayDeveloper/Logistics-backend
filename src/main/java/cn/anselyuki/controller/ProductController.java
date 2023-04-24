package cn.anselyuki.controller;

import cn.anselyuki.controller.response.Result;
import cn.anselyuki.repository.ProductRepository;
import cn.anselyuki.repository.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Product", description = "物资相关接口")
@RequestMapping("product")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @PutMapping("add")
    @Operation(summary = "添加物资", description = "添加物资")
    public ResponseEntity<Result<Product>> addProduct() {
        return null;
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除物资", description = "删除物资")
    public ResponseEntity<Result<Product>> deleteProduct(@PathVariable String id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("update")
    @Operation(summary = "更新物资", description = "更新物资")
    public ResponseEntity<Result<Product>> updateProduct() {
        return null;
    }

    @GetMapping("get")
    @Operation(summary = "获取物资", description = "获取物资")
    public ResponseEntity<Result<Product>> getProduct() {
        return null;
    }
}
