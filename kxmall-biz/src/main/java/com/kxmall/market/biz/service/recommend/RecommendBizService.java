package com.kxmall.market.biz.service.recommend;

import com.kxmall.market.core.Const;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.dto.RecommendDTO;
import com.kxmall.market.data.mapper.RecommendMapper;
import com.kxmall.market.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-11
 * Time: 下午6:08
 *
 * @author kaixin
 */
@Service
public class RecommendBizService {

    @Resource
    private CacheComponent cacheComponent;

    @Resource
    private RecommendMapper recommendMapper;

    private static final String RECOMMEND_NAME = "RECOMMEND_TYPE_";

    public Page<RecommendDTO> getRecommendByType(Long storageId, Integer recommendType, Integer pageNo, Integer pageSize) throws ServiceException {
        //缓存key
        String keyCache = RECOMMEND_NAME + recommendType + "_" + storageId + "_" + pageNo + "_" + pageSize;
        //若关键字为空，尝试从缓存取列表
        Page objFromCache = cacheComponent.getObj(keyCache, Page.class);
        if (objFromCache != null) {
            //return objFromCache;
        }
        List<RecommendDTO> recommendDTOList = recommendMapper.getRecommendByStorage(storageId, recommendType, new RowBounds((pageNo - 1) * pageSize, pageSize));
        Integer count = recommendMapper.getRecommendByStorageCount(storageId, recommendType);
        Page<RecommendDTO> page = new Page<>(recommendDTOList, pageNo, pageSize, count);
        if (!CollectionUtils.isEmpty(recommendDTOList)) {
            cacheComponent.putObj(keyCache, page, Const.CACHE_ONE_DAY);
        }
        return page;
    }

    public Page<RecommendDTO> queryAllRecommend(Integer pageNo, Integer pageSize) throws ServiceException {
        Integer count = recommendMapper.getAllRecommendCount();
        List<RecommendDTO> recommendDTOList = recommendMapper.getAllRecommend((pageNo - 1) * pageSize, pageSize);
        Page<RecommendDTO> page = new Page<>(recommendDTOList, pageNo, pageSize, count);
        return page;
    }
}
