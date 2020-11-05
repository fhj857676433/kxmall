package com.kxmall.market.biz.service.goods;

import com.baomidou.mybatisplus.entity.Column;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.biz.service.appriaise.AppraiseBizService;
import com.kxmall.market.biz.service.category.CategoryBizService;
import com.kxmall.market.biz.service.collect.CollectBizService;
import com.kxmall.market.biz.service.footpring.FootprintBizService;
import com.kxmall.market.biz.service.freight.FreightBizService;
import com.kxmall.market.core.Const;
import com.kxmall.market.core.exception.AppServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.core.exception.ServiceExceptionDefinition;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.CategoryDO;
import com.kxmall.market.data.domain.SkuDO;
import com.kxmall.market.data.domain.SpuAttributeDO;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.domain.StockDO;
import com.kxmall.market.data.dto.StockDTO;
import com.kxmall.market.data.dto.appraise.AppraiseResponseDTO;
import com.kxmall.market.data.dto.freight.FreightTemplateDTO;
import com.kxmall.market.data.dto.goods.SkuDTO;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.enums.BizType;
import com.kxmall.market.data.enums.SpuStatusType;
import com.kxmall.market.data.mapper.CategoryMapper;
import com.kxmall.market.data.mapper.ImgMapper;
import com.kxmall.market.data.mapper.SkuMapper;
import com.kxmall.market.data.mapper.SpuAttributeMapper;
import com.kxmall.market.data.mapper.SpuMapper;
import com.kxmall.market.data.mapper.StockMapper;
import com.kxmall.market.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 * @date 2019/7/12
 */
@Service
public class GoodsBizService {

    /**
     * SPU 分页缓存
     */
    public static final String CA_SPU_PAGE_PREFIX = "CA_SPU_PAGE_";

    /**
     * SPU DTO 缓存  CA_SPU_+spuId
     */
    public static final String CA_SPU_PREFIX = "CA_SPU_";

    /**
     * SPU 销量缓存
     */
    private static final String CA_SPU_SALES_HASH = "CA_SPU_SALES_HASH";

    /**
     * SPU DO 缓存，加速 getById...  hashKey = 'S' + spuId
     */
    private static final String CA_SPU_HASH = "CA_SPU_HASH";

    @Resource
    private ImgMapper imgMapper;

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SpuAttributeMapper spuAttributeMapper;

    @Autowired
    private CategoryBizService categoryBizService;

    @Autowired
    private FreightBizService freightBizService;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private CollectBizService collectBizService;

    @Autowired
    private FootprintBizService footprintBizService;

    @Autowired
    private AppraiseBizService appraiseBizService;

    @Resource
    private StockMapper stockMapper;

    private static final Column[] baseColumns = {
            Column.create().column("id"),
            Column.create().column("original_price").as("originalPrice"),
            Column.create().column("price"),
            Column.create().column("vip_price").as("vipPrice"),
            Column.create().column("title"),
            Column.create().column("sales"),
            Column.create().column("img"),
            Column.create().column("description"),
            Column.create().column("category_id").as("categoryId"),
            Column.create().column("freight_template_id").as("freightTemplateId"),
            Column.create().column("unit"),
            Column.create().column("status")};

    public Page<SpuDTO> getGoodsPage(Integer pageNo, Integer pageSize, Long categoryId, String orderBy, Boolean isAsc, String title) throws ServiceException {
        Wrapper<SpuDO> wrapper = new EntityWrapper<>();


        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        } else {
            //若关键字为空，尝试从缓存取列表
            Page objFromCache = cacheComponent.getObj(CA_SPU_PAGE_PREFIX + categoryId + "_" + pageNo + "_" + pageSize + "_" + orderBy + "_" + isAsc, Page.class);
            if (objFromCache != null) {
                return objFromCache;
            }
        }

        if (orderBy != null && isAsc != null) {
            wrapper.orderBy(orderBy, isAsc);
        }

        if (categoryId != null && categoryId != 0L) {
            List<CategoryDO> childrenList = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", categoryId));

            if (CollectionUtils.isEmpty(childrenList)) {
                //目标节点为叶子节点,即三级类目
                wrapper.eq("category_id", categoryId);
            } else {
                //目标节点存在子节点
                LinkedList<Long> childrenIds = new LinkedList<>();
                CategoryDO categoryDO = categoryMapper.selectById(categoryId);

                // 检验传入类目是一级还是二级类目
                if (categoryDO.getParentId() != 0L) {
                    //二级分类
                    childrenList.forEach(item -> {
                        childrenIds.add(item.getId());
                    });
                } else {
                    //一级分类
                    childrenList.forEach(item -> {
                        List<CategoryDO> leafList = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", item.getId()));
                        if (!CollectionUtils.isEmpty(leafList)) {
                            leafList.forEach(leafItem -> {
                                childrenIds.add(leafItem.getId());
                            });
                        }
                    });
                }
                wrapper.in("category_id", childrenIds);
            }
        }

        wrapper.eq("status", SpuStatusType.SELLING.getCode());
        wrapper.setSqlSelect(baseColumns);
        List<SpuDO> spuDOS = spuMapper.selectPage(new RowBounds((pageNo - 1) * pageSize, pageSize), wrapper);
        //组装SPU
        List<SpuDTO> spuDTOList = new ArrayList<>();
        spuDOS.forEach(item -> {
            SpuDTO spuDTO = new SpuDTO();
            BeanUtils.copyProperties(item, spuDTO);
            Map<String, String> hashAll = cacheComponent.getHashAll(CA_SPU_SALES_HASH);
            if (hashAll != null) {
                String salesStr = hashAll.get("S" + item.getId());
                if (!StringUtils.isEmpty(salesStr)) {
                    //spuDTO.setSales(new Integer(salesStr));
                }
            }
            spuDTOList.add(spuDTO);
        });

        Integer count = spuMapper.selectCount(wrapper);
        Page<SpuDTO> page = new Page<>(spuDTOList, pageNo, pageSize, count);
        if (StringUtils.isEmpty(title)) {
            //若关键字为空，制作缓存
            cacheComponent.putObj(CA_SPU_PAGE_PREFIX + categoryId + "_" + pageNo + "_" + pageSize + "_" + orderBy + "_" + isAsc, page, Const.CACHE_ONE_DAY);
        }
        return page;
    }


    /**
     * 通过Id获取SpuDO 领域对象
     *
     * @param spuId
     * @return
     * @throws ServiceException
     */
    public SpuDO getSpuById(Long spuId) throws ServiceException {
        SpuDO objFromCache = cacheComponent.getHashObj(CA_SPU_HASH, "S" + spuId, SpuDO.class);
        if (objFromCache != null) {
            return objFromCache;
        }
        SpuDO spuDO = spuMapper.selectById(spuId);
        if (spuDO == null) {
            throw new AppServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        cacheComponent.putHashObj(CA_SPU_HASH, "S" + spuDO, spuDO, Const.CACHE_ONE_DAY);
        return spuDO;
    }

    public SpuDTO getGoods(Long spuId, Long userId) throws ServiceException {
        SpuDTO spuDTOFromCache = cacheComponent.getObj(CA_SPU_PREFIX + spuId, SpuDTO.class);
        if (spuDTOFromCache != null) {
            packSpuCollectInfo(spuDTOFromCache, userId);
            //获取第一页评论
            Page<AppraiseResponseDTO> spuAppraise = appraiseBizService.getSpuAllAppraise(spuId, 1, 10, 1);
            spuDTOFromCache.setAppraisePage(spuAppraise);
            if (userId != null) {
                footprintBizService.addOrUpdateFootprint(userId, spuId);
            }
            //todo
            //return spuDTOFromCache;
        }
        SpuDO spuDO = spuMapper.selectById(spuId);
        SpuDTO spuDTO = new SpuDTO();
        BeanUtils.copyProperties(spuDO, spuDTO);
        spuDTO.setImgList(imgMapper.getImgs(BizType.GOODS.getCode(), spuId));
        List<SkuDO> skuDOList = skuMapper.selectList(
                new EntityWrapper<SkuDO>()
                        .eq("spu_id", spuId));
        spuDTO.setSkuList(skuDOList);
        //类目族
        spuDTO.setCategoryIds(categoryBizService.getCategoryFamily(spuDO.getCategoryId()));
        //无用代码
        String salesStr = cacheComponent.getHashRaw(CA_SPU_SALES_HASH, "S" + spuId);
        //获取商品属性
        List<SpuAttributeDO> spuAttributeList = spuAttributeMapper.selectList(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        spuDTO.setAttributeList(spuAttributeList);
        //获取运费模板
        FreightTemplateDTO templateDTO = freightBizService.getTemplateById(spuDO.getFreightTemplateId());
        spuDTO.setFreightTemplate(templateDTO);
        //放入缓存
        cacheComponent.putObj(CA_SPU_PREFIX + spuId, spuDTO, Const.CACHE_ONE_DAY);
        packSpuCollectInfo(spuDTO, userId);
        //获取第一页评论
        Page<AppraiseResponseDTO> spuAppraise = appraiseBizService.getSpuAllAppraise(spuId, 1, 10, 1);
        spuDTO.setAppraisePage(spuAppraise);
        if (userId != null) {
            footprintBizService.addOrUpdateFootprint(userId, spuId);
        }
        return spuDTO;
    }

    public void clearGoodsCache(Long spuId) {

        cacheComponent.del(CA_SPU_PREFIX + spuId);

        cacheComponent.delPrefixKey(CA_SPU_PAGE_PREFIX);

        cacheComponent.delHashObj(CA_SPU_HASH, "S" + spuId);

    }

    private void packSpuCollectInfo(SpuDTO spuDTO, Long userId) throws ServiceException {
        if (userId != null) {
            Boolean collectStatus = collectBizService.getCollectBySpuId(spuDTO.getId(), userId);
            spuDTO.setCollect(collectStatus);
        }
    }

    /**
     * 制定仓库下搜索Goods列表
     *
     * @param storageId  仓库id
     * @param pageNo     页码
     * @param pageSize   页数
     * @param categoryId 分类
     * @param orderBy    排序对象
     * @param isAsc      升序降序
     * @param title      标题
     * @return 分页商品对象
     */
    public Page<SpuDTO> getGoodsPageByStorage(Long storageId, Integer pageNo, Integer pageSize, Long categoryId, String orderBy, Boolean isAsc, String title) throws ServiceException {
        //缓存key
        String keyCache = CA_SPU_PAGE_PREFIX + storageId + "_" + categoryId + "_" + pageNo + "_" + pageSize + "_" + orderBy + "_" + isAsc;
        //开始组装条件search
        if (StringUtils.isEmpty(title)) {
            //若关键字为空，尝试从缓存取列表
            Page objFromCache = cacheComponent.getObj(keyCache, Page.class);
            if (objFromCache != null) {
                //return objFromCache;
            }
        }
        LinkedList<Long> childrenIds = new LinkedList<>();
        if (categoryId != null && categoryId != 0L) {
            List<CategoryDO> childrenList = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", categoryId));
            if (!CollectionUtils.isEmpty(childrenList)) {
                 //一级分类
                 childrenList.forEach(item -> childrenIds.add(item.getId()));
                 //使用in查询，就不需要等于查询
                 categoryId = null;
            }
        }
        //查询商品结果
        List<SpuDTO> spuDTOList = spuMapper.selectPageByStorage(new RowBounds((pageNo - 1) * pageSize, pageSize), title, categoryId, childrenIds, storageId, orderBy, isAsc);
        //查出总数
        Integer count = spuMapper.selectCountByStorage(title, categoryId, childrenIds, storageId, orderBy, isAsc);
        Page<SpuDTO> page = new Page<>(spuDTOList, pageNo, pageSize, count);
        if (StringUtils.isEmpty(title)) {
            //若关键字为空，制作缓存
            cacheComponent.putObj(keyCache, page, Const.CACHE_ONE_DAY);
        }
        return page;
    }

    /**
     * 获取制定仓库下的商品
     *
     * @param storageId 仓库id
     * @param spuId     商品id
     * @param userId    用户id
     * @param activityId 活动id
     * @param couponId  优惠券id
     * @return 商品对象
     */
    public SpuDTO getGoodsByStorage(Long storageId, Long spuId, Long userId, Long activityId, Long couponId) throws ServiceException {
        //获取缓存
        SpuDTO cacheComponentObj = cacheComponent.getObj(CA_SPU_PREFIX + storageId + "_" + spuId, SpuDTO.class);
        if (cacheComponentObj != null) {
            //不懂什么意思
            packSpuCollectInfo(cacheComponentObj, userId);
            //获取第一页评论
            Page<AppraiseResponseDTO> spuAppraise = appraiseBizService.getSpuAllAppraise(spuId, 1, 10, 1);
            cacheComponentObj.setAppraisePage(spuAppraise);
            //新增该用户查看印记
            if (userId != null) {
                footprintBizService.addOrUpdateFootprint(userId, spuId);
            }
            //return cacheComponentObj;
        }
        //是否为活动商品
        SpuDO spuDO;
        if (0!=activityId && 0!=couponId) {
            spuDO = spuMapper.selectByActivityGood(activityId,couponId,spuId);
        }else{
            spuDO = spuMapper.selectById(spuId);
        }
        if (ObjectUtils.isEmpty(spuDO)) {
            throw new AppServiceException(new ServiceExceptionDefinition(404,"商品对象不存在"));
        }
        SpuDTO spuDTO = new SpuDTO();
        BeanUtils.copyProperties(spuDO, spuDTO);
        spuDTO.setImgList(imgMapper.getImgs(BizType.GOODS.getCode(), spuId));
        //商品规格信息
        SkuDTO skuDTO = new SkuDTO();
        skuDTO.setActivityId(activityId);
        skuDTO.setCouponId(couponId);
        spuDTO.setSkuDto(skuDTO);
        //查询库存信息
        StockDO stockDO = new StockDO();
        stockDO.setSpuId(spuId);
        stockDO.setStorageId(storageId);
        stockDO = stockMapper.selectOne(stockDO);
        StockDTO stockDTO = new StockDTO();
        if (!ObjectUtils.isEmpty(stockDO)) {
            BeanUtils.copyProperties(stockDO, stockDTO);
        }
        spuDTO.setStockDto(stockDTO);
        //类目族
        spuDTO.setCategoryIds(categoryBizService.getCategoryFamily(spuDO.getCategoryId()));
        //获取商品属性
        List<SpuAttributeDO> spuAttributeList = spuAttributeMapper.selectList(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        spuDTO.setAttributeList(spuAttributeList);
        //获取运费模板
        FreightTemplateDTO templateDTO = freightBizService.getTemplateById(spuDO.getFreightTemplateId());
        spuDTO.setFreightTemplate(templateDTO);
        //放入缓存
        cacheComponent.putObj(CA_SPU_PREFIX + storageId + "_" + spuId, spuDTO, Const.CACHE_ONE_DAY);
        packSpuCollectInfo(spuDTO, userId);
        //获取第一页评论
        Page<AppraiseResponseDTO> spuAppraise = appraiseBizService.getSpuAllAppraise(spuId, 1, 10, 1);
        spuDTO.setAppraisePage(spuAppraise);
        if (userId != null) {
            footprintBizService.addOrUpdateFootprint(userId, spuId);
        }
        return spuDTO;
    }
}
