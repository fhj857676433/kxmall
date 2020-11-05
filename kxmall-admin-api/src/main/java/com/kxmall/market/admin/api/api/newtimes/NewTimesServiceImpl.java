package com.kxmall.market.admin.api.api.newtimes;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.NewTimesDO;
import com.kxmall.market.data.domain.StorageDO;
import com.kxmall.market.data.mapper.NewTimesMapper;
import com.kxmall.market.data.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewTimesServiceImpl implements NewTimesService{

    private static final Integer STOP_STATES =1;//新鲜时报停止，隐藏内容

    private static final Integer START_STATES= 0;//新鲜时报开启，显示内容

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private NewTimesMapper newTimesMapper;

    @Override
    public List<StorageDO> storagAllName(String name, Long adminId) throws ServiceException {
        Wrapper<StorageDO> wrapper =new EntityWrapper<>();
        wrapper.like("name",name);
        List<StorageDO> storageDOS =storageMapper.selectList(wrapper);
        List<StorageDO> storageDOList =new ArrayList<StorageDO>();
        for (StorageDO storageDO : storageDOS){
            StorageDO storageDO1 =new StorageDO();
            storageDO1.setId(storageDO.getId());
            storageDO1.setName(storageDO.getName());
            storageDOList.add(storageDO1);
        }
        return storageDOList;
    }

    @Override
    public NewTimesDO getNewTimes(Integer id, Long adminId) throws ServiceException {
        NewTimesDO newTimesDO =new NewTimesDO();
        newTimesDO.setStorageId(id);
        NewTimesDO newTimesDO1 =newTimesMapper.selectOne(newTimesDO);
        return newTimesDO1;
    }

    @Override
    public NewTimesDO updageOrAdd(NewTimesDO newTimesDO, Long adminId) throws ServiceException {
        NewTimesDO newTimesDO1 =new NewTimesDO();
        newTimesDO1.setStorageId(newTimesDO.getStorageId());
        NewTimesDO newTimesDO2 =newTimesMapper.selectOne(newTimesDO1);
        if (newTimesDO2!=null){
            //更新时报
            newTimesDO.setGmtUpdate(new Date());
            Wrapper<NewTimesDO> wrapper =new EntityWrapper<>();
            wrapper.eq("storage_id",newTimesDO.getStorageId());
            if(newTimesMapper.update(newTimesDO,wrapper)<=0){
                throw new AdminServiceException(ExceptionDefinition.NEW_TIMES_UPDATE);
            }
        }else {
            //添加时报
            newTimesDO.setGmtCreate(new Date());
            newTimesDO.setGmtUpdate(new Date());
            if(newTimesMapper.insert(newTimesDO)<=0){
                throw new AdminServiceException(ExceptionDefinition.NEW_TIMES_INSERT);
            }
        }
        return newTimesDO;
    }
}
