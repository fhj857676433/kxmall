package com.kxmall.market.biz.service.collect;

import com.kxmall.market.core.Const;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.mapper.CollectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author admin
 * @date 2019/7/12
 */
@Service
public class CollectBizService {

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private CacheComponent cacheComponent;

    public static final String CA_USER_COLLECT_HASH = "CA_USER_COLLECT_";

    public Boolean getCollectBySpuId(Long spuId, Long userId) throws ServiceException {
        boolean hasKey = cacheComponent.hasKey(CA_USER_COLLECT_HASH + userId);
        if (!hasKey) {
            //若没有Key，则添加缓存
            List<String> spuIds = collectMapper.getUserCollectSpuIds(userId);
            if (CollectionUtils.isEmpty(spuIds)) {
                //redis set不能为空
                spuIds.add("0");
            }
            cacheComponent.putSetRawAll(CA_USER_COLLECT_HASH + userId, spuIds.toArray(new String[0]), Const.CACHE_ONE_DAY);
        }
        return cacheComponent.isSetMember(CA_USER_COLLECT_HASH + userId, spuId + "");
    }

}
