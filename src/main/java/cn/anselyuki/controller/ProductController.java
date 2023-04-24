package cn.anselyuki.controller;

import cn.anselyuki.controller.request.ProductDTO;
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
    public ResponseEntity<Result<Product>> addProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        try {
            productRepository.save(product);
        } catch (Exception e) {
            Result.fail(403, "添加物资失败");
        }
        return Result.success(product);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除物资", description = "删除物资")
    public ResponseEntity<Result<Product>> deleteProduct(@PathVariable String id) {
        if (!productRepository.existsById(id)) {
            return Result.fail(404, "物资不存在");
        }
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            Result.fail(403, "删除物资失败");
        }
        return Result.success(null);
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
