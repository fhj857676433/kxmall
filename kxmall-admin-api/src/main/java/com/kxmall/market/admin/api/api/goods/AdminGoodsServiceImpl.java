package com.kxmall.market.admin.api.api.goods;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.entity.Column;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.biz.service.goods.GoodsBizService;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.CartDO;
import com.kxmall.market.data.domain.CategoryDO;
import com.kxmall.market.data.domain.ImgDO;
import com.kxmall.market.data.domain.SkuDO;
import com.kxmall.market.data.domain.SpuAttributeDO;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.dto.goods.SpuTreeNodeDTO;
import com.kxmall.market.data.enums.BizType;
import com.kxmall.market.data.enums.SpuStatusType;
import com.kxmall.market.data.mapper.CartMapper;
import com.kxmall.market.data.mapper.CategoryMapper;
import com.kxmall.market.data.mapper.ImgMapper;
import com.kxmall.market.data.mapper.SkuMapper;
import com.kxmall.market.data.mapper.SpuAttributeMapper;
import com.kxmall.market.data.mapper.SpuMapper;
import com.kxmall.market.data.model.Page;
import com.kxmall.market.plugin.core.inter.IPluginUpdateGoods;
import com.kxmall.market.plugin.core.manager.PluginsManager;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 2019/7/11.
 */
@Service
public class AdminGoodsServiceImpl implements AdminGoodsService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private SpuAttributeMapper spuAttributeMapper;

    @Resource
    private ImgMapper imgMapper;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private GoodsBizService goodsBizService;

    @Resource
    private PluginsManager pluginsManager;

    @Resource
    private CacheComponent cacheComponent;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    private static final Column[] spuBaseColumns = {
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

    /**
     * 后台低频接口， 无需缓存 -新接口
     *
     * @return
     * @throws ServiceException
     */
    public List<SpuTreeNodeDTO> getSpuBigTree(Long adminId) throws ServiceException {
        List<CategoryDO> categoryDOS = categoryMapper.selectList(new EntityWrapper<CategoryDO>().orderBy("level"));
        if (categoryDOS != null && categoryDOS.size() > 0) {
            Integer recordLevelOne = 0;
            List<SpuDO> spuDOS = spuMapper.getSpuTitleAll();
            List<SpuTreeNodeDTO> list = new ArrayList<>();
            for (CategoryDO categoryDO : categoryDOS) {
                if (categoryDO.getLevel().equals(0)) {
                    recordLevelOne++;
                }
            }
            Integer recordLevelTwo = categoryDOS.size();
            for (int i = 0; i < recordLevelOne; i++) {
                CategoryDO categoryOnI = categoryDOS.get(i);    //一级类目
                SpuTreeNodeDTO dtoOnI = new SpuTreeNodeDTO();
                dtoOnI.setLabel(categoryOnI.getTitle());
                dtoOnI.setValue("C_" + categoryOnI.getId());
                dtoOnI.setId(categoryOnI.getId());
                dtoOnI.setChildren(new LinkedList<>());
                for (int j = recordLevelOne; j < recordLevelTwo; j++) {
                    CategoryDO categoryOnJ = categoryDOS.get(j);    //二级类目
                    if (!categoryOnJ.getParentId().equals(dtoOnI.getId())) {
                        continue;
                    }
                    SpuTreeNodeDTO dtoOnJ = new SpuTreeNodeDTO();
                    dtoOnJ.setLabel(categoryOnJ.getTitle());
                    dtoOnJ.setValue("C_" + categoryOnJ.getId());
                    dtoOnJ.setId(categoryOnJ.getId());
                    dtoOnJ.setChildren(new LinkedList<>());
                    for (int k = 0; k < spuDOS.size(); k++) {
                        if (k != 0 && spuDOS.get(k - 1).getCategoryId().equals(dtoOnJ.getId()) && !spuDOS.get(k).getCategoryId().equals(dtoOnJ.getId())) {
                            break;
                        }
                        SpuDO spuDO = spuDOS.get(k);        //商品
                        if (spuDO.getCategoryId().equals(dtoOnJ.getId())) {
                            SpuTreeNodeDTO dtoOnK = new SpuTreeNodeDTO();
                            dtoOnK.setLabel(spuDO.getTitle());
                            dtoOnK.setValue("G_" + spuDO.getId());
                            dtoOnK.setId(spuDO.getId());
                            dtoOnK.setChildren(new LinkedList<>());
                            dtoOnJ.getChildren().add(dtoOnK);
                        }
                    }
                    dtoOnI.getChildren().add(dtoOnJ);
                }
                list.add(dtoOnI);
            }
            return list;
        }
        return null;
    }


    /**
     * 后台低频接口， 无需缓存 旧接口
     * @return
     * @throws ServiceException
     */
    public List<SpuTreeNodeDTO> getSpuBigTree_old(Long adminId) throws  ServiceException{
        List<CategoryDO> categoryDOS = categoryMapper.selectList(new EntityWrapper<CategoryDO>().orderBy("level"));
        List<SpuDO> spuDOS = spuMapper.getSpuTitleAll();
        List<SpuTreeNodeDTO> list = new ArrayList<>();
        Integer recordLevelOne = 0;
        Integer recordLevelTwo = 0;
        for (int i = 0; i < categoryDOS.size(); i++) {
            if(i != 0 && categoryDOS.get(i-1).getLevel().equals(0) && categoryDOS.get(i).getLevel().equals(1)){
                recordLevelOne = i;
            }
            if(i != 0 && categoryDOS.get(i-1).getLevel().equals(1) && categoryDOS.get(i).getLevel().equals(2)){
                recordLevelTwo = i;
                break;
            }
        }
        for (int i = 0; i < recordLevelOne; i++) {
            CategoryDO categoryOnI = categoryDOS.get(i);    //一级类目
            SpuTreeNodeDTO dtoOnI = new SpuTreeNodeDTO();
            dtoOnI.setLabel(categoryOnI.getTitle());
            dtoOnI.setValue("C_" + categoryOnI.getId());
            dtoOnI.setId(categoryOnI.getId());
            dtoOnI.setChildren(new LinkedList<>());
            for (int j = recordLevelOne; j < recordLevelTwo; j++) {
                CategoryDO categoryOnJ = categoryDOS.get(j);    //二级类目
                if(!categoryOnJ.getParentId().equals(dtoOnI.getId())){
                    continue;
                }
                SpuTreeNodeDTO dtoOnJ = new SpuTreeNodeDTO();
                dtoOnJ.setLabel(categoryOnJ.getTitle());
                dtoOnJ.setValue("C_" + categoryOnJ.getId());
                dtoOnJ.setId(categoryOnJ.getId());
                dtoOnJ.setChildren(new LinkedList<>());

                for (int p = recordLevelTwo; p <categoryDOS.size() ; p++) {
                    CategoryDO categoryOnP = categoryDOS.get(p);    //三级类目
                    if(!categoryOnP.getParentId().equals(dtoOnJ.getId())){
                        continue;
                    }

                    SpuTreeNodeDTO dtoOnP = new SpuTreeNodeDTO();
                    dtoOnP.setLabel(categoryOnP.getTitle());
                    dtoOnP.setValue("C_" + categoryOnP.getId());
                    dtoOnP.setId(categoryOnP.getId());
                    dtoOnP.setChildren(new LinkedList<>());

                    for (int k = 0; k < spuDOS.size(); k++) {
                        if(k != 0 && spuDOS.get(k-1).getCategoryId().equals(dtoOnP.getId()) && !spuDOS.get(k).getCategoryId().equals(dtoOnP.getId())){
                            break;
                        }
                        SpuDO spuDO = spuDOS.get(k);        //商品
                        if(spuDO.getCategoryId().equals(dtoOnP.getId())){
                            SpuTreeNodeDTO dtoOnK = new SpuTreeNodeDTO();
                            dtoOnK.setLabel(spuDO.getTitle());
                            dtoOnK.setValue("G_" + spuDO.getId());
                            dtoOnK.setId(spuDO.getId());
                            dtoOnK.setChildren(new LinkedList<>());
                            dtoOnP.getChildren().add(dtoOnK);
                        }
                    }
                    dtoOnJ.getChildren().add(dtoOnP);
                }
                dtoOnI.getChildren().add(dtoOnJ);
            }
            list.add(dtoOnI);
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SpuDTO spuDTO, Long adminId) throws ServiceException {
        //参数校验
        if (CollectionUtils.isEmpty(spuDTO.getSkuList())) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_SKU_LIST_EMPTY);
        }
        if (spuDTO.getId() != null) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_CREATE_HAS_ID);
        }
        //校验Sku是否重复
        List<String> barCodes = spuDTO.getSkuList().stream().map(item -> item.getBarCode()).collect(Collectors.toList());
        List<SkuDO> existSkuDO = skuMapper.selectList(new EntityWrapper<SkuDO>().in("bar_code", barCodes));
        if (!CollectionUtils.isEmpty(existSkuDO)) {
            String spuIds = existSkuDO.stream().map(item -> item.getSpuId().toString()).collect(Collectors.joining(","));
            String skuIds = existSkuDO.stream().map(item -> item.getBarCode()).collect(Collectors.joining(","));
            throw new AdminServiceException(ExceptionDefinition
                    .buildVariableException(ExceptionDefinition.GOODS_CREATE_BARCODE_REPEAT, spuIds, skuIds));
        }
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(spuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuDO.setGmtCreate(now);
        spuMapper.insert(spuDO);
        spuDTO.setId(spuDO.getId());
        //插入SKU表
        for (SkuDO skuDO : spuDTO.getSkuList()) {
            /*if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new AdminServiceException(ExceptionDefinition.GOODS_PRICE_CHECKED_FAILED);
            }*/
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            skuDO.setGmtCreate(now);
            //skuDO.setFreezeStock(0);
            skuMapper.insert(skuDO);
        }
        //插入spuAttr
        insertSpuAttribute(spuDTO, now);
        //插入IMG
        insertSpuImg(spuDTO, spuDO.getId(), now);
        goodsBizService.clearGoodsCache(spuDO.getId());
        pluginUpdateInvoke(spuDO.getId());

        cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(SpuDTO spuDTO, Long adminId) throws ServiceException {
        if (spuDTO.getId() == null) {
            throw new AdminServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        if (CollectionUtils.isEmpty(spuDTO.getSkuList())) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_SKU_LIST_EMPTY);
        }
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(spuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuMapper.updateById(spuDO);
        List<String> barCodes = new LinkedList<>();
        for (SkuDO skuDO : spuDTO.getSkuList()) {
            /*if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new AdminServiceException(ExceptionDefinition.GOODS_PRICE_CHECKED_FAILED);
            }*/
            skuDO.setId(null);
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            //skuDO.setFreezeStock(0);
            if (skuMapper.update(skuDO,
                    new EntityWrapper<SkuDO>()
                            .eq("bar_code", skuDO.getBarCode())) <= 0) {
                skuDO.setGmtCreate(now);
                skuMapper.insert(skuDO);
            }
            barCodes.add(skuDO.getBarCode());
        }
        //删除多余barCode
        skuMapper.delete(new EntityWrapper<SkuDO>().eq("spu_id", spuDO.getId()).notIn("bar_code", barCodes));
        //插入spuAttr
        spuAttributeMapper.delete(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuDTO.getId()));
        insertSpuAttribute(spuDTO, now);
        imgMapper.delete(new EntityWrapper<ImgDO>().eq("biz_id", spuDO.getId()).eq("biz_type", BizType.GOODS.getCode()));
        //插入IMG
        insertSpuImg(spuDTO, spuDO.getId(), now);
        goodsBizService.clearGoodsCache(spuDTO.getId());
        pluginUpdateInvoke(spuDTO.getId());

        cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
        return "ok";
    }

    private void insertSpuAttribute(SpuDTO spuDTO, Date now) {
        if (!CollectionUtils.isEmpty(spuDTO.getAttributeList())) {
            for (SpuAttributeDO spuAttributeDO : spuDTO.getAttributeList()) {
                spuAttributeDO.setSpuId(spuDTO.getId());
                spuAttributeDO.setGmtUpdate(now);
                spuAttributeDO.setGmtCreate(now);
                spuAttributeMapper.insert(spuAttributeDO);
            }
        }
    }

    private void insertSpuImg(SpuDTO spuDTO, Long bizId, Date now) {
        List<String> imgList = spuDTO.getImgList();
        List<ImgDO> imgDOList = imgList.stream().map(item -> {
            ImgDO imgDO = new ImgDO();
            imgDO.setBizType(BizType.GOODS.getCode());
            imgDO.setBizId(bizId);
            imgDO.setUrl(item);
            imgDO.setGmtCreate(now);
            imgDO.setGmtUpdate(now);
            return imgDO;
        }).collect(Collectors.toList());
        imgMapper.insertImgs(imgDOList);
    }

    @Override
    public Page<SpuDTO> list(Integer page, Integer limit, Long categoryId, String title, String barcode, Integer status,String idsNotInJson, Long adminId) throws ServiceException {
        Wrapper<SpuDO> wrapper = new EntityWrapper<>();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(idsNotInJson)) {
            List<Long> ids = JSONObject.parseArray(idsNotInJson, Long.class);
            LinkedList<Long> idsNotInSearch = new LinkedList<>(ids);
            wrapper.notIn("id", idsNotInSearch);
        }

        if (categoryId != null && categoryId != 0L) {
            List<CategoryDO> childrenList = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", categoryId));
            if (!CollectionUtils.isEmpty(childrenList)) {
                LinkedList<Long> childrenIds = new LinkedList<>();
                //一级分类
                childrenList.forEach(item -> childrenIds.add(item.getId()));
                //使用in查询，就不需要等于查询
                wrapper.in("category_id", childrenIds);
            } else {
                wrapper.eq("category_id", categoryId);
            }
        }

        if (status != null) {
            wrapper.eq("status", status.intValue() <= SpuStatusType.STOCK.getCode() ? SpuStatusType.STOCK.getCode() : SpuStatusType.SELLING.getCode());
        }

        if (barcode != null) {
            List<SkuDO> skuDOList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("bar_code", barcode));
            if (!CollectionUtils.isEmpty(skuDOList)) {
                SkuDO skuDO = skuDOList.get(0);
                wrapper.eq("id", skuDO.getSpuId());
            }
        }

        wrapper.setSqlSelect(spuBaseColumns);
        List<SpuDO> spuDOS = spuMapper.selectPage(new RowBounds((page - 1) * limit, limit), wrapper);

        //组装SPU
        List<SpuDTO> spuDTOList = new ArrayList<>();
        spuDOS.forEach(item -> {
            SpuDTO spuDTO = new SpuDTO();
            BeanUtils.copyProperties(item, spuDTO);
            List<SkuDO> skuDOList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("spu_id", item.getId()));
            spuDTO.setSkuList(skuDOList);
            spuDTOList.add(spuDTO);
        });

        Integer count = spuMapper.selectCount(wrapper);
        Page<SpuDTO> dtoPage = new Page<>(spuDTOList, page, limit, count);

        return dtoPage;
    }

    @Override
    public SpuDTO detail(Long spuId, Long adminId) throws ServiceException {
        return goodsBizService.getGoods(spuId, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long spuId, Long adminId) throws ServiceException {
        if (spuMapper.deleteById(spuId) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        cartMapper.delete(new EntityWrapper<CartDO>().in("sku_id", skuMapper.getSkuIds(spuId)));
        skuMapper.delete(new EntityWrapper<SkuDO>().eq("spu_id", spuId));
        imgMapper.delete(new EntityWrapper<ImgDO>().eq("biz_id", spuId).eq("biz_type", BizType.GOODS.getCode()));
        spuAttributeMapper.delete(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        goodsBizService.clearGoodsCache(spuId);
        pluginUpdateInvoke(spuId);

        cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String batchDelete(String idsJson, Long adminId) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        if (CollectionUtils.isEmpty(ids)) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        if (spuMapper.delete(new EntityWrapper<SpuDO>().in("id", ids)) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        List<Long> skuIds = skuMapper.selectSkuIdsBySpuIds(ids);
        cartMapper.delete(new EntityWrapper<CartDO>().in("sku_id", skuIds));
        skuMapper.delete(new EntityWrapper<SkuDO>().in("spu_id", ids));
        imgMapper.delete(new EntityWrapper<ImgDO>().in("biz_id", ids).eq("biz_type", BizType.GOODS.getCode()));
        spuAttributeMapper.delete(new EntityWrapper<SpuAttributeDO>().in("spu_id", ids));
        for (Long spuId : ids) {
            goodsBizService.clearGoodsCache(spuId);
        }
        return "ok";
    }

    @Override
    public List<SpuDTO> detailByIds(String idsJson, Long adminId) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        LinkedList<Long> idsSearch = new LinkedList<>(ids);
        Wrapper<SpuDO> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect(spuBaseColumns);
        wrapper.in("id",idsSearch);
        List<SpuDO> spuDoList = spuMapper.selectList(wrapper);
        //组装SPU
        List<SpuDTO> spuDtoList = new ArrayList<>();
        spuDoList.forEach(item -> {
            SpuDTO spuDTO = new SpuDTO();
            BeanUtils.copyProperties(item, spuDTO);
            List<SkuDO> skuDoList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("spu_id", item.getId()));
            spuDTO.setSkuList(skuDoList);
            spuDtoList.add(spuDTO);
        });
        return spuDtoList;
    }

    private void pluginUpdateInvoke(Long spuId) {
        List<IPluginUpdateGoods> plugins = pluginsManager.getPlugins(IPluginUpdateGoods.class);
        if (!CollectionUtils.isEmpty(plugins)) {
            for (IPluginUpdateGoods updateGoods : plugins) {
                updateGoods.invokeGoodsUpdate(spuId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpuDTO freezeOrActivation(Long spuId, Integer status, Long adminId) throws ServiceException {
        SpuDO spuDO = spuMapper.selectById(spuId);

        if (spuDO == null) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }

        status = status <= SpuStatusType.STOCK.getCode() ? SpuStatusType.STOCK.getCode() : SpuStatusType.SELLING.getCode();

        if (spuDO.getStatus().intValue() == status.intValue()) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NEED_STATUS_ERROR);
        }

        spuDO.setStatus(status);
        spuDO.setGmtUpdate(new Date());
        if (spuMapper.updateById(spuDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_UPDATE_SQL_FAILED);
        }

        SpuDTO spuDTO = new SpuDTO();
        BeanUtils.copyProperties(spuDO, spuDTO);
        List<SkuDO> skuDOList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("spu_id", spuDO.getId()));
        spuDTO.setSkuList(skuDOList);

        cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
        return spuDTO;
    }
}
