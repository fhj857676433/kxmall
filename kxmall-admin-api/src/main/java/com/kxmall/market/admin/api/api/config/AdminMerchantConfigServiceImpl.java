package com.kxmall.market.admin.api.api.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.biz.service.config.ConfigBizService;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.ConfigDO;
import com.kxmall.market.data.mapper.ConfigMapper;
import com.kxmall.market.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-20
 * Time: 上午10:47
 */
@Service
public class AdminMerchantConfigServiceImpl implements AdminMerchantConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private ConfigBizService configBizService;

    @Override
    public Page<ConfigDO> list(Integer state, String keyWord, String valueWorth, Integer page, Integer limit, Long adminId) {
        Wrapper<ConfigDO> wrapper = new EntityWrapper<>();
        if (state != null) {
            wrapper.eq("state", state);
        }
        if (!StringUtils.isEmpty(keyWord)) {
            wrapper.like("key_word", keyWord);
        }
        if (!StringUtils.isEmpty(valueWorth)) {
            wrapper.eq("value_worth", valueWorth);
        }
        List<ConfigDO> configDOS = configMapper.selectPage(new RowBounds((page - 1) * limit, limit), wrapper);
        Integer count = configMapper.selectCount(wrapper);
        return new Page<>(configDOS, page, limit, count);
    }

    @Override
    public ConfigDO addConfig(Long adminId, String keyWord, String valueWorth, String description) throws ServiceException {
        configMapper.selectList(new EntityWrapper<ConfigDO>().eq("key_word", keyWord));

        ConfigDO configDO = new ConfigDO();
        configDO.setKeyWord(keyWord);
        configDO.setValueWorth(valueWorth);
        configDO.setDescription(description);
        Date date = new Date();
        configDO.setGmtCreate(date);
        configDO.setGmtUpdate(date);
        if (configMapper.insert(configDO) > 0) {
            return configDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public ConfigDO updateConfig(Long adminId, Long id, String keyWord, String valueWorth, String description) throws ServiceException {
        ConfigDO configDOPo = configMapper.selectById(id);
        configMapper.selectList(new EntityWrapper<ConfigDO>().eq("key_word", keyWord));
        ConfigDO configDO = new ConfigDO();
        configDO.setId(configDOPo.getId());
        configDO.setKeyWord(keyWord);
        configDO.setValueWorth(valueWorth);
        configDO.setDescription(description);
        configDO.setGmtUpdate(new Date());
        if (configMapper.updateById(configDO) > 0) {
            return configDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public ConfigDO configDetail(Long adminId, Long id) throws ServiceException {
        return configMapper.selectById(id);
    }
}
