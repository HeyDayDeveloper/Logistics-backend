package cn.anselyuki.service.impl;

import cn.anselyuki.controller.response.OutStockVO;
import cn.anselyuki.controller.response.UserInfoVO;
import cn.anselyuki.repository.OutStockRepository;
import cn.anselyuki.repository.ProductRepository;
import cn.anselyuki.repository.UserRepository;
import cn.anselyuki.repository.entity.OutStock;
import cn.anselyuki.service.OutStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutStockServiceImpl implements OutStockService {
    private final OutStockRepository outStockRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
    public OutStockVO queryForOutStock(String id) {
        OutStock outStock = outStockRepository.findById(id).orElse(null);
        if (outStock == null) {
            return null;
        }
        OutStockVO outStockVO = new OutStockVO();
        BeanUtils.copyProperties(outStock, outStockVO);
        outStockVO.setProduct(productRepository.findById(outStock.getPid()).orElse(null));
        outStockVO.setUserInfo(new UserInfoVO(userRepository.findById(outStock.getUid()).orElse(null)));
        return outStockVO;
    }

    @Override
    public List<OutStockVO> listForOutStock() {
        List<OutStock> outStockList = outStockRepository.findAll();
        List<OutStockVO> outStockVOList = new ArrayList<>();
        for (OutStock outStock : outStockList) {
            OutStockVO outStockVO = new OutStockVO();
            BeanUtils.copyProperties(outStock, outStockVO);
            outStockVO.setProduct(productRepository.findById(outStock.getPid()).orElse(null));
            outStockVO.setUserInfo(new UserInfoVO(userRepository.findById(outStock.getUid()).orElse(null)));
            outStockVOList.add(outStockVO);
        }
        return outStockVOList;
    }
}
