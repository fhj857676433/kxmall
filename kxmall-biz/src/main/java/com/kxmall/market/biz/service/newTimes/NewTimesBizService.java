package com.kxmall.market.biz.service.newTimes;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.data.domain.NewTimesDO;
import com.kxmall.market.data.enums.NewTimesStopType;
import com.kxmall.market.data.mapper.NewTimesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/09 20:14
 **/
@Service
public class NewTimesBizService {

    @Autowired
    private NewTimesMapper newTimesMapper;

    private static final String INIT_STR = "您想不到的新鲜时蔬马上就涞.";

    public String getNewTimesContentByStorageId(Long storageId) {
        Wrapper<NewTimesDO> wrapper = new EntityWrapper<>();
        wrapper.eq("storage_id", storageId);
        wrapper.eq("is_stop", NewTimesStopType.START_STATES.getCode());
        List<NewTimesDO> newTimesDOList = newTimesMapper.selectList(wrapper);
        if (newTimesDOList != null && newTimesDOList.size() > 0) {
            return newTimesDOList.get(0).getContent();
        }
        return INIT_STR;
    }

}
