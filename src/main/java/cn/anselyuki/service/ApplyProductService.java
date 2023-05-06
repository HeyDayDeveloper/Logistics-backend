package cn.anselyuki.service;

import cn.anselyuki.controller.request.ApplyInfoDTO;
import cn.anselyuki.controller.response.ApplyInfoVO;

public interface ApplyProductService {
    ApplyInfoVO apply(ApplyInfoDTO applyInfoDTO);

    ApplyInfoVO validation(ApplyInfoDTO applyInfoDTO);
}
