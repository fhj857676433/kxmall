package com.kxmall.market.biz.service.appriaise;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kxmall.market.core.Const;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.AppraiseDO;
import com.kxmall.market.data.dto.appraise.AppraiseResponseDTO;
import com.kxmall.market.data.enums.BizType;
import com.kxmall.market.data.mapper.AppraiseMapper;
import com.kxmall.market.data.mapper.ImgMapper;
import com.kxmall.market.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2019/7/13.
 */
@Service
public class AppraiseBizService {

    @Autowired
    private AppraiseMapper appraiseMapper;
    @Autowired
    private CacheComponent cacheComponent;
    @Autowired
    private ImgMapper imgMapper;

    public static final String CA_APPRAISE_KEY = "CA_APPRAISE_";

    public Page<AppraiseResponseDTO> getSpuAllAppraise(Long spuId, Integer pageNo, Integer pageSize, Integer state) throws ServiceException {
        String cacheKey = CA_APPRAISE_KEY + spuId + "_" + pageNo + "_" + pageSize;
        Page obj = cacheComponent.getObj(cacheKey, Page.class);
        if (obj != null) {
            return obj;
        }
        Integer count = appraiseMapper.selectCount(new EntityWrapper<AppraiseDO>().eq("spu_id", spuId).eq("state", state));
        Integer offset = pageSize * (pageNo - 1);
        List<AppraiseResponseDTO> appraiseResponseDTOS = appraiseMapper.selectSpuAllAppraise(spuId, offset, pageSize);
        for (AppraiseResponseDTO appraiseResponseDTO : appraiseResponseDTOS) {
            appraiseResponseDTO.setImgList(imgMapper.getImgs(BizType.COMMENT.getCode(), appraiseResponseDTO.getId()));
        }
        Page<AppraiseResponseDTO> page = new Page<>(appraiseResponseDTOS, pageNo, pageSize, count);
        cacheComponent.putObj(cacheKey, page, Const.CACHE_ONE_DAY);
        return page;
    }

}
