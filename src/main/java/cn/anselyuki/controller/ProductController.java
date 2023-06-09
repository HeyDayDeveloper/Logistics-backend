package cn.anselyuki.controller;

import cn.anselyuki.common.utils.JpaUtils;
import cn.anselyuki.controller.request.ProductDTO;
import cn.anselyuki.controller.response.ProductVO;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.repository.ProductCategoryRepository;
import cn.anselyuki.repository.ProductRepository;
import cn.anselyuki.repository.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "Product", description = "物资相关接口")
@RequestMapping("product")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;

    /**
     * 添加物资
     *
     * @param productDTO 物资DTO
     * @return 添加成功的物资信息(携带生成的UUID信息)
     */
    @PutMapping("add")
    @Operation(summary = "添加物资", description = "添加物资，UUID由内部生成并返回")
    public ResponseEntity<Result<Product>> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product;
        try {
            if ((productDTO.getCategoryId() == null || productDTO.getCategoryId().isBlank())) {
                //用分类名称添加物资
                if (productDTO.getCategory() != null && !productDTO.getCategory().isBlank()) {
                    String categoryId = categoryRepository.findByName(productDTO.getCategory()).getId();
                    product = new Product(productDTO).setCategoryId(categoryId);
                } else {
                    product = new Product(productDTO);
                }
            } else {
                product = new Product(productDTO);
            }
            product.setCreateTime(new Date());
            productRepository.save(product);
        } catch (DataIntegrityViolationException exception) {
            return Result.fail(403, "物资已经存在");
        } catch (NullPointerException exception) {
            return Result.fail(404, "物资分类不存在");
        } catch (Exception exception) {
            exception.printStackTrace();
            return Result.fail(500, "系统内部失败,请检查日志");
        }
        return Result.success(product);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除物资", description = "根据ID删除物资")
    public ResponseEntity<Result<Product>> deleteProduct(@PathVariable String id) {
        if (!productRepository.existsById(id)) return Result.fail(404, "物资不存在");
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            return Result.fail(403, "删除物资失败");
        }
        return Result.success(null);
    }

    /**
     * 更新物资，通过传入参数的id更新物资参数
     *
     * @param product 新的物资对象
     * @return 修改后的物资信息
     */
    @PatchMapping("update")
    @Operation(summary = "更新物资", description = "更新物资,更新物资，通过传入参数的id更新物资参数")
    public ResponseEntity<Result<Product>> updateProduct(@RequestBody Product product) {
        if (product.getId() == null || product.getId().isBlank()) return Result.fail(404, "物资ID不能为空");
        Product save = productRepository.findById(product.getId()).orElse(null);
        if (save != null) {
            // 通过工具类将非空属性拷贝到save中
            JpaUtils.copyNotNullProperties(product, save);
            // 更新修改时间
            product.setModifiedTime(new Date());
        } else {
            return Result.fail(404, "该物资不存在");
        }
        try {
            save.setModifiedTime(new Date());
            productRepository.save(save);
        } catch (Exception e) {
            return Result.fail(403, "更新物资失败");
        }
        return Result.success(save);
    }

    @GetMapping("list")
    @Operation(summary = "获取物资列表", description = "获取物资列表")
    public ResponseEntity<Result<List<ProductVO>>> getProduct() {
        List<Product> productList = productRepository.findAll();
        List<ProductVO> productVOList = productList.stream().map(ProductVO::convert).toList();
        for (ProductVO productVO : productVOList) {
            try {
                productVO.setCategory(categoryRepository.findById(productVO.getCategoryId()).orElse(null));
            } catch (Exception e) {
                log.error("物资分类不存在");
            }
        }
        return Result.success(productVOList);
    }

    @GetMapping("query/{name}")
    @Operation(summary = "根据物资名称查询物资", description = "根据物资名称查询物资")
    public ResponseEntity<Result<List<ProductVO>>> queryProduct(@PathVariable String name) {
        List<Product> productList = productRepository.findByNameLike('%' + name);
        List<ProductVO> productVOList = productList.stream().map(ProductVO::convert).toList();
        for (ProductVO productVO : productVOList) {
            productVO.setCategory(categoryRepository.findById(productVO.getCategoryId()).orElse(null));
        }
        return Result.success(productVOList);
    }

    @GetMapping("queryById/{id}")
    @Operation(summary = "根据物资ID查询物资", description = "根据物资ID查询物资")
    public ResponseEntity<Result<ProductVO>> queryProductById(@PathVariable String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return Result.fail(404, "物资不存在");
        ProductVO productVO = ProductVO.convert(product);
        productVO.setCategory(categoryRepository.findById(productVO.getCategoryId()).orElse(null));
        return Result.success(productVO);
    }
}
