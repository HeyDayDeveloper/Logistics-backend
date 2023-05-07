package cn.anselyuki.service.impl;

import cn.anselyuki.controller.request.ApplyInfoDTO;
import cn.anselyuki.controller.response.ApplyInfoVO;
import cn.anselyuki.controller.response.OutStockVO;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.controller.response.UserInfoVO;
import cn.anselyuki.repository.ApplyProductRepository;
import cn.anselyuki.repository.OutStockRepository;
import cn.anselyuki.repository.ProductRepository;
import cn.anselyuki.repository.UserRepository;
import cn.anselyuki.repository.entity.ApplyProduct;
import cn.anselyuki.repository.entity.OutStock;
import cn.anselyuki.repository.entity.Product;
import cn.anselyuki.repository.entity.User;
import cn.anselyuki.service.ApplyProductService;
import cn.anselyuki.service.OutStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyProductServiceImpl implements ApplyProductService {
    private final ApplyProductRepository applyProductRepository;
    private final OutStockRepository outStockRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OutStockService outStockService;

    @Override
    public ApplyInfoVO apply(ApplyInfoDTO applyInfoDTO) {
        ApplyProduct applyProduct = new ApplyProduct();
        log.info("applyInfoDTO:{}", applyInfoDTO);
        BeanUtils.copyProperties(applyInfoDTO, applyProduct);
        log.info("applyProduct:{}", applyProduct);
        ApplyInfoVO applyInfoVO = new ApplyInfoVO();
        String uid = applyInfoDTO.getUid();
        User user = userRepository.findById(uid).orElse(null);
        UserInfoVO userInfoVO = new UserInfoVO();
        if (user != null) {
            BeanUtils.copyProperties(user, userInfoVO);
        }
        ;
        applyInfoVO.setUserInfo(userInfoVO);
        applyProduct.setPhoneNumber(userInfoVO.getPhoneNumber());
        applyProduct.setEmail(userInfoVO.getEmail());
        applyProduct.setStatus(0);
        applyProduct.setCreateTime(new Date());
        BeanUtils.copyProperties(applyProductRepository.save(applyProduct), applyInfoVO);
        return applyInfoVO;
    }

    @Override
    public ResponseEntity<Result<OutStockVO>> validation(String id, String pid, String remark) {
        if (id == null || pid == null) {
            return Result.fail(404, "参数为空");
        }
        ApplyProduct applyProduct = applyProductRepository.findById(id).orElse(null);
        if (applyProduct == null) {
            return Result.fail(404, "申请单不存在");
        }
        Integer applyNum = applyProduct.getNum();
        Product product = productRepository.findById(pid).orElse(null);
        if (product == null) {
            return Result.fail(404, "物资不存在");
        }
        Integer num = product.getNum();
        if (num < applyNum) {
            return Result.fail(403, "物资数量不足");
        }
        // 申请单状态改为已通过
        OutStock outStock;
        try {
            product.setNum(num - applyNum);
            productRepository.save(product);
            outStock = new OutStock();
            // 生成出库单
            outStock.setPid(pid)
                    .setUid(applyProduct.getUid())
                    .setNum(applyNum)
                    .setAddress(applyProduct.getAddress())
                    .setCreateTime(new Date())
                    .setRemark(remark);
            outStock = outStockRepository.save(outStock);
        } catch (Exception e) {
            return Result.fail(500, "出库失败");
        }
        applyProductRepository.deleteById(id);
        return Result.success(outStockService.queryForOutStock(outStock.getId()));
    }

    @Override
    public List<ApplyProduct> getListByDegree() {
        return applyProductRepository.findByOrderByDegreeDesc();
    }
}
