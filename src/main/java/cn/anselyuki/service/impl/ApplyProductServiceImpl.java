package cn.anselyuki.service.impl;

import cn.anselyuki.controller.request.ApplyInfoDTO;
import cn.anselyuki.controller.response.ApplyInfoVO;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.controller.response.UserInfoVO;
import cn.anselyuki.repository.ApplyProductRepository;
import cn.anselyuki.repository.ProductRepository;
import cn.anselyuki.repository.UserRepository;
import cn.anselyuki.repository.entity.ApplyProduct;
import cn.anselyuki.repository.entity.Product;
import cn.anselyuki.repository.entity.User;
import cn.anselyuki.service.ApplyProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyProductServiceImpl implements ApplyProductService {
    private final ApplyProductRepository applyProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ApplyInfoVO apply(ApplyInfoDTO applyInfoDTO) {
        ApplyProduct applyProduct = new ApplyProduct();
        BeanUtils.copyProperties(applyInfoDTO, applyProduct);

        ApplyInfoVO applyInfoVO = new ApplyInfoVO();
        String uid = applyInfoDTO.getUid();
        User user = userRepository.findById(uid).orElse(null);
        UserInfoVO userInfoVO = new UserInfoVO();
        if (user != null) {
            BeanUtils.copyProperties(user, userInfoVO);
        }
        applyInfoVO.setUserInfo(userInfoVO);
        applyProduct.setPhoneNumber(userInfoVO.getPhoneNumber());
        applyProduct.setEmail(userInfoVO.getEmail());
        applyProduct.setStatus(0);
        applyProduct.setCreateTime(new Date());
        BeanUtils.copyProperties(applyProductRepository.save(applyProduct), applyInfoVO);
        return applyInfoVO;
    }

    @Override
    public ResponseEntity<Result<Object>> validation(String id, String pid) {
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
        product.setNum(num - applyNum);
        applyProduct.setStatus(1);
        applyProduct.setPid(pid);
        productRepository.save(product);
        applyProductRepository.save(applyProduct);
        return Result.success(null);
    }

    @Override
    public List<ApplyProduct> getListByDegree() {
        return applyProductRepository.findByOrderByDegreeDesc();
    }
}
