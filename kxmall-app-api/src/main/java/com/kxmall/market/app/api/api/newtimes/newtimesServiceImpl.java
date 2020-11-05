package com.kxmall.market.app.api.api.newtimes;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.NewTimesDO;
import com.kxmall.market.data.mapper.NewTimesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class newtimesServiceImpl implements newtimesService {
    private static final Integer STOP_STATES =1;

    private static final Integer START_STATES= 0;

    @Autowired
    private NewTimesMapper newTimesMapper;

    @Override
    public List<NewTimesDO> getNewTimes() throws ServiceException {
        Wrapper<NewTimesDO> wrapper =new EntityWrapper<>();
        wrapper.eq("is_stop",START_STATES);
        List<NewTimesDO> newTimesDOS =newTimesMapper.selectList(wrapper);
        return newTimesDOS;
    }
}
