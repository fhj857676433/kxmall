package com.kxmall.market.admin.api.api.recommend;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kxmall.market.biz.service.recommend.RecommendBizService;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.RecommendDO;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.dto.EnumsDTO;
import com.kxmall.market.data.dto.RecommendDTO;
import com.kxmall.market.data.enums.RecommendType;
import com.kxmall.market.data.mapper.RecommendMapper;
import com.kxmall.market.data.mapper.SpuMapper;
import com.kxmall.market.data.model.Page;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 下午5:47
 * @author kaixin
 */
@Service
public class AdminRecommendServiceImpl implements AdminRecommendService {

    @Resource
    private CacheComponent cacheComponent;
    @Resource
    private RecommendMapper recommendMapper;
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private RecommendBizService recommendBizService;

    private final static String RECOMMEND_NAME = "RECOMMEND_TYPE_";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addRecommend(Long adminId, Long spuId, Integer recommendType) throws ServiceException {

        if (!(spuMapper.selectCount(new EntityWrapper<SpuDO>().eq("id", spuId)) > 0)) {
            throw new AdminServiceException(ExceptionDefinition.RECOMMEND_SPU_NO_HAS);
        }

        if (recommendMapper.selectCount(new EntityWrapper<RecommendDO>()
                .eq("spu_id", spuId)
                .eq("recommend_type", recommendType)) > 0) {
            throw new AdminServiceException(ExceptionDefinition.RECOMMEND_ALREADY_HAS);
        }
        RecommendDO recommendDO = new RecommendDO(spuId, recommendType);
        Date now = new Date();
        recommendDO.setGmtCreate(now);
        recommendDO.setGmtUpdate(now);
        if (!(recommendMapper.insert(recommendDO) > 0)) {
            throw new AdminServiceException(ExceptionDefinition.RECOMMEND_SQL_ADD_FAILED);
        }
        cacheComponent.delPrefixKey(RECOMMEND_NAME+recommendType);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addRecommendBatch(Long adminId, String idsJson, Integer recommendType) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        if (CollectionUtils.isEmpty(ids)) {
            throw new AdminServiceException(ExceptionDefinition.RECOMMEND_SPU_NO_HAS);
        }
        List<RecommendDO> recommendDOList = new ArrayList<>();
        for (Long id : ids) {
            RecommendDO recommendDO = new RecommendDO(id, recommendType);
            Date now = new Date();
            recommendDO.setGmtCreate(now);
            recommendDO.setGmtUpdate(now);
            recommendDOList.add(recommendDO);
        }
        recommendMapper.insertBtach(recommendDOList);
        cacheComponent.delPrefixKey(RECOMMEND_NAME+recommendType);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRecommend(Long adminId, Long id, Integer recommendType) throws ServiceException {

        Integer judgeSQL = recommendMapper.delete(new EntityWrapper<RecommendDO>()
                .eq("id", id)
                .eq("recommend_type",recommendType));

        if(judgeSQL > 0){
            cacheComponent.delPrefixKey(RECOMMEND_NAME + recommendType);
            return true;
        }

        throw new AdminServiceException(ExceptionDefinition.RECOMMEND_SQL_DELETE_FAILED);
    }

    @Override
    public Page<RecommendDTO> queryRecommendByType(Long adminId, Integer recommendType, Integer pageNo, Integer pageSize) throws ServiceException {
        if(recommendType == null){
            return recommendBizService.queryAllRecommend(pageNo, pageSize);
        }
        Integer count = recommendMapper.getRecommendByTypeCount(recommendType);
        List<RecommendDTO> recommendDTOList = recommendMapper.getRecommendByType(recommendType,(pageNo-1)*pageSize,pageSize);
        return new Page<>(recommendDTOList,pageNo,pageSize,count);
    }

    @Override
    public Page<RecommendDTO> queryAllRecommend(Long adminId, Integer pageNo, Integer pageSize) throws ServiceException {
        return recommendBizService.queryAllRecommend(pageNo, pageSize);
    }

    @Override
    public List<EnumsDTO> getRecommendTypeEnums() throws ServiceException {
        List<EnumsDTO> recommendEnums = new ArrayList<>();
        for (RecommendType value : RecommendType.values()) {
            EnumsDTO enumsDTO = new EnumsDTO();
            enumsDTO.setCode(value.getCode());
            enumsDTO.setMsg(value.getMsg());
            recommendEnums.add(enumsDTO);
        }
        return recommendEnums;
    }
}
