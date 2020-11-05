package com.kxmall.market.app.api.api.recommend;

import com.kxmall.market.biz.service.recommend.RecommendBizService;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.RecommendDTO;
import com.kxmall.market.data.model.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 下午3:40
 * @author kaixin
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    private RecommendBizService recommendBizService;

    @Override
    public Page<RecommendDTO> getRecommendByStorage(Long storageId, Integer recommedType, Integer pageNo, Integer pageSize) throws ServiceException {
        return recommendBizService.getRecommendByType(storageId,recommedType,pageNo,pageSize);
    }
}
