package com.kxmall.market.data.dto.rider;

import com.kxmall.market.data.domain.RiderDO;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: fy
 * @date: 2020/02/28 20:35
 **/
@Data
public class RiderDTO extends RiderDO {

    private List<Integer> weekNumberIds;
}
