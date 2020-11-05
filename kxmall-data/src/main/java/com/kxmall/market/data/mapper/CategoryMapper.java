package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.CategoryDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/7/2.
 */
public interface CategoryMapper extends BaseMapper<CategoryDO> {

    /**
     * 根据子节点获取所有父节点的数据
     *
     * @param childId 子节点ID
     * @return 结果
     * @author wangxiongfei
     * @date 2020-02-21
     */
    List<CategoryDO> getCategoryParentByChildId(Long childId);

    /**
     * 根据子节点查询全部父节点
     * @param id
     * @return
     */
    CategoryDO selectCategoryById(@Param("id") Long id);



}
