package com.kxmall.market.admin.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.kxmall.market.admin.service.StockFileService;
import com.kxmall.market.admin.util.ExcelUtils;
import com.kxmall.market.data.dto.ErrorDataDTO;
import com.kxmall.market.data.dto.StockDTO;
import com.kxmall.market.data.dto.StockExcelDTO;
import com.kxmall.market.data.mapper.SkuMapper;
import com.kxmall.market.data.mapper.StorageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author: Mr Wang
 * @date: 2020/2/22
 * @time: 19:50
 */

@Controller
@RequestMapping("/stock")
@Slf4j
@SuppressWarnings("all")
public class StockFileController {


    @Autowired
    private StockFileService stockFileService;

    /**
     * 批量添加库存
     *
     * @param file excel
     * @return 响应结果
     * @author wxf
     * @date 2020/2/22
     */
    @PostMapping("/insertBatch")
    @ResponseBody
    public Map insertBatch(@RequestParam("file") MultipartFile file,HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        if (file.isEmpty()) {
            map.put("errno",400);
            map.put("errmsg","文件解析异常!");
            return map;
        }
        try {
            List<StockExcelDTO> list = ExcelUtils.importExcel(file,StockExcelDTO.class);
            List<StockDTO> stockDTOS = stockFileService.insertBatch(list,request);
            map.put("data",stockDTOS);
            map.put("errno",200);
            map.put("errmsg","成功");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            log.error("批量导入异常：{}",e.getMessage());
            map.put("errno",400);
            map.put("errmsg","批量导入异常!");
            return map;
        }
    }

    /**
     * 库存模板
     *
     * @param response
     */
    @RequestMapping(value = "/exportOld", method = RequestMethod.GET)
    public void exportOld(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        List<StockExcelDTO> stockExcelDTOS = stockFileService.stockExport();
        try {
            log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
            ExcelUtils.exportExcel(stockExcelDTOS, "库存信息表", "库存信息", StockExcelDTO.class, "库存模板", response);
        }catch (Exception e){
            log.error("导出excel失败!>>{}",e.getMessage());
        }
    }
    /**
     * 库存模板
     *
     * @param
     */
    @RequestMapping(value = "/exportErrorData", method = RequestMethod.GET)
    public void exportErrorData(HttpServletRequest request, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        List<ErrorDataDTO> errorDataDTOs = stockFileService.exportErrorData(request);
        try {
            log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
            if(errorDataDTOs == null || errorDataDTOs.size() == 0){
                errorDataDTOs = new ArrayList<>();
                ErrorDataDTO errorDataDTO = new ErrorDataDTO();
                errorDataDTOs.add(errorDataDTO);
            }
            ExcelUtils.exportExcel(errorDataDTOs, "错误信息表", "错误信息", ErrorDataDTO.class, "错误信息", response);
        }catch (Exception e){
            e.printStackTrace();
            log.error("导出excel失败!>>{}",e.getMessage());
        }
    }

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private SkuMapper skuMapper;

    /**
     * 库存模板
     *
     * @param response
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response,HttpServletRequest request) throws IOException {
        List<StockExcelDTO> stockExcelDTOS = new ArrayList<>();
        StockExcelDTO stockExcelDTO = new StockExcelDTO();
        stockExcelDTOS.add(stockExcelDTO);
        List<Long> longs = storageMapper.selectIds();
        List<String> strings = skuMapper.selectCodes();
        String[] ids = new String[longs.size()];
        for (int i = 0; i < longs.size(); i++) {
            ids[i] = longs.get(i).toString();
        }
        String[] codes = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            codes[i] = strings.get(i).toString();
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("库存模板", "库存模板", ExcelType.HSSF),StockExcelDTO.class,stockExcelDTOS);
        ExcelUtils.selectList(workbook, 0, 0, ids);
        ExcelUtils.selectList(workbook, 1, 1, codes);
        ExcelUtils.downLoadExcel("库存模板",workbook, request, response);
    }
}
