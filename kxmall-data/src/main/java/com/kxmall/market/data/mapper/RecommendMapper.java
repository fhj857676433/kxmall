package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.RecommendDTO;
import com.kxmall.market.data.domain.RecommendDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 下午3:32
 * @author kaixin
 */
public interface RecommendMapper extends BaseMapper<RecommendDO> {

    /**
     * 根据仓库，查找商品信息
     * @param storageId 仓库id
     * @param recommendType 推荐类型
     * @param rowBounds 分页对象
     * @return 推荐对象
     */
    public List<RecommendDTO> getRecommendByStorage(@Param("storageId") Long storageId, @Param("recommendType") Integer recommendType, @Param("rowBounds") RowBounds rowBounds);

    /**
     * 根据推荐类型，查找商品信息
     * @param recommendType 推荐类型
     * @param offset 页码
     * @param pageSize 页数
     * @return 推荐对象
     */
    public List<RecommendDTO> getRecommendByType(@Param("recommendType") Integer recommendType, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    public List<RecommendDTO> getAllRecommend(@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);

    /**
     * 根据仓库，查找商品信息
     *
     * @param storageId 仓库id
     * @param recommendType 推荐类型
     * @return 总数
     */
    Integer getRecommendByStorageCount(@Param("storageId") Long storageId, @Param("recommendType") Integer recommendType);

    /**
     * 推荐查询所有个数
     * @return 数量
     */
    Integer getAllRecommendCount();

    /**
     * 推荐查询指定个数
     * @param recommendType 类型
     * @return 数量
     */
    Integer getRecommendByTypeCount(@Param("recommendType") Integer recommendType);

    /**
     * 批量新增推荐管理
     * @param recommendDOList 推荐集合
     * @return 成功条数
     */
    Integer insertBtach(@Param("list") List<RecommendDO> recommendDOList);
}
