package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.UserDTO;
import com.kxmall.market.data.domain.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2019/7/1.
 */
public interface UserMapper extends BaseMapper<UserDO> {

    public UserDTO login(@Param("phone") String phone, @Param("cryptPassword") String cryptPassword);

    public List<UserDO> getUserList(
            @Param("phone") String phone, @Param("nickname") String nickname,
            @Param("level") Integer level, @Param("gender") Integer gender,
            @Param("status") Integer status, @Param("offset") Integer offset,
            @Param("limit") Integer limit);

    public Integer countUser(
            @Param("phone") String phone, @Param("nickname") String nickname,
            @Param("level") Integer level, @Param("gender") Integer gender,
            @Param("status") Integer status);

    /**
     * 总用户数
     * @param gmtCreate
     * @return
     */
    Integer countTotalUser(
            @Param("gmtCreate") Date gmtCreate);

    /**
     * 新注册用户数
     * @param start
     * @param end
     * @return
     */
    Integer countNewUser(
            @Param("start") Date start,@Param("end") Date end);

    /**
     * 在线用户数
     * @param start
     * @param end
     * @return
     */
    Integer countOnlineUser(
            @Param("start") Date start,@Param("end") Date end);

    /**
     * 下单用户数
     * @param start
     * @param end
     * @return
     */
    Integer countOrderUser(
            @Param("start") Date start,@Param("end") Date end);

    /**
     * 首单用户数
     * @param start
     * @param end
     * @return
     */
    Integer countFirstOrderUser(
            @Param("start") Date start,@Param("end") Date end);
}
