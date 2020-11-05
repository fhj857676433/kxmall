package com.kxmall.market.app.api.api.groupshop;

import com.kxmall.market.data.dto.goods.GroupShopDTO;
import com.kxmall.market.data.mapper.GroupShopMapper;
import com.kxmall.market.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2019/11/24
 * Time: 15:26
 */
@Service
public class GroupShopServiceImpl implements GroupShopService {

    @Autowired
    private GroupShopMapper groupShopMapper;

    @Override
    public Page<GroupShopDTO> getGroupShopPage(Integer pageNo, Integer pageSize) throws ServerException {
        Integer count = groupShopMapper.selectCount(null);
        List<GroupShopDTO> groupShopPage = groupShopMapper.getGroupShopPage((pageNo - 1) * pageSize, pageSize);
        return new Page<>(groupShopPage, pageNo, pageSize, count);
    }


}
