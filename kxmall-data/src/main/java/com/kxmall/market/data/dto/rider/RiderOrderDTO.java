package com.kxmall.market.data.dto.rider;

import com.kxmall.market.data.domain.RiderOrderDO;
import com.kxmall.market.data.domain.RiderSpuDO;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/02 17:35
 **/
@Data
public class RiderOrderDTO extends RiderOrderDO {

    private List<RiderSpuDO> riderSpuDOList;
}
