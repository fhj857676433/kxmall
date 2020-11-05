package com.kxmall.market.admin.service;

import com.kxmall.market.data.dto.ErrorDataDTO;
import com.kxmall.market.data.dto.StockDTO;
import com.kxmall.market.data.dto.StockExcelDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: Mr Wang
 * @date: 2020/2/23
 * @time: 9:10
 */
public interface StockFileService {


    /**
     * 批量添加库存
     *
     * @return 响应结果
     * @author wxf
     * @date 2020/2/22
     */
    List<StockDTO> insertBatch(List<StockExcelDTO> list, HttpServletRequest request);

    /**
     * 库存模板
     * @return
     */
    List<StockExcelDTO> stockExport();

    /**
     * 错误数据
     * @return
     */
    List<ErrorDataDTO> exportErrorData(HttpServletRequest request);


}
