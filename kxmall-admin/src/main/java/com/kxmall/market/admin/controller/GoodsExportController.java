package com.kxmall.market.admin.controller;

import com.baomidou.mybatisplus.entity.Column;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.admin.api.api.goods.AdminGoodsServiceImpl;
import com.kxmall.market.admin.util.ExcelUtils;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.CategoryDO;
import com.kxmall.market.data.domain.SkuDO;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.enums.SpuStatusType;
import com.kxmall.market.data.mapper.CategoryMapper;
import com.kxmall.market.data.mapper.SkuMapper;
import com.kxmall.market.data.mapper.SpuMapper;
import com.kxmall.market.data.mapper.StorageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author: Mr Wang
 * @date: 2020/2/22
 * @time: 19:50
 */

@Controller
@RequestMapping("/goods")
@Slf4j
@SuppressWarnings("all")
public class GoodsExportController {

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private SkuMapper skuMapper;

    private AdminGoodsServiceImpl adminGoodsService;

    /**
     * 商品导出
     *
     * @param response
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportExcel(@RequestParam(name = "titles",required = false) List<String> titles, @RequestParam(name = "categoryId",required = false) Long categoryId,@RequestParam(name = "title",required = false) String title,@RequestParam(name = "barcode",required = false) String barcode,@RequestParam(name = "status",required = false) Integer status,@RequestParam(name = "adminId",required = false) Long adminId,HttpServletResponse response,HttpServletRequest request) throws Exception {
        List<SpuDTO> list = list(categoryId, title, barcode, status, adminId);
        //List<String> titles2 = Arrays.asList("title","categoryId","originalPrice","detail","description","freightTemplateId","status","unit","id","barCode","skuTitle");
        SXSSFWorkbook workbook = exportToExcel(titles, list);
        ExcelUtils.downLoadExcel("商品导出",workbook, request, response);
    }
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SpuMapper spuMapper;

    public List<SpuDTO> list(Long categoryId, String title, String barcode, Integer status, Long adminId) throws ServiceException {
        Wrapper<SpuDO> wrapper = new EntityWrapper<>();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }

        if (categoryId != null && categoryId != 0L) {
            List<CategoryDO> childrenList = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", categoryId));
            if (!CollectionUtils.isEmpty(childrenList)) {
                LinkedList<Long> childrenIds = new LinkedList<>();
                //一级分类
                childrenList.forEach(item -> childrenIds.add(item.getId()));
                //使用in查询，就不需要等于查询
                wrapper.in("category_id", childrenIds);
            } else {
                wrapper.eq("category_id", categoryId);
            }
        }

        if (status != null) {
            wrapper.eq("status", status.intValue() <= SpuStatusType.STOCK.getCode() ? SpuStatusType.STOCK.getCode() : SpuStatusType.SELLING.getCode());
        }

        if (barcode != null) {
            List<SkuDO> skuDOList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("bar_code", barcode));
            if (!CollectionUtils.isEmpty(skuDOList)) {
                SkuDO skuDO = skuDOList.get(0);
                wrapper.eq("id", skuDO.getSpuId());
            }
        }

        wrapper.setSqlSelect(spuBaseColumns);

        List<SpuDO> spuDOS = spuMapper.selectList(wrapper);
        //组装SPU
        List<SpuDTO> spuDTOList = new ArrayList<>();
        spuDOS.forEach(item -> {
            SpuDTO spuDTO = new SpuDTO();
            BeanUtils.copyProperties(item, spuDTO);
            List<SkuDO> skuDOList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("spu_id", item.getId()));
            spuDTO.setSkuList(skuDOList);
            spuDTOList.add(spuDTO);
        });

        return spuDTOList;
    }
    private static final Column[] spuBaseColumns = {
            Column.create().column("id"),
            Column.create().column("original_price").as("originalPrice"),
            Column.create().column("price"),
            Column.create().column("vip_price").as("vipPrice"),
            Column.create().column("title"),
            Column.create().column("sales"),
            Column.create().column("img"),
            Column.create().column("description"),
            Column.create().column("category_id").as("categoryId"),
            Column.create().column("freight_template_id").as("freightTemplateId"),
            Column.create().column("unit"),
            Column.create().column("status")};

   private static Map<String,String> map = new LinkedHashMap<>();
   static {
        map.put("title","商品名称");
        map.put("categoryId","目录ID");
        map.put("originalPrice","原价");
        map.put("detail","详情");
        map.put("description","描述");
        map.put("freightTemplateId","运费");
        map.put("status","是否在售");
        map.put("unit","单位");
        map.put("spuId","商品ID");

        map.put("barCode","商品条码");
        map.put("skuTitle","规格名称");
   }



    public static SXSSFWorkbook exportToExcel(List<String> titles, List<SpuDTO> exportDatas) throws Exception{
        List<Map<String, Object>> list = new ArrayList<>();
        for (SpuDTO spuDTO:exportDatas) {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("title",spuDTO.getTitle());
            hashMap.put("categoryId",spuDTO.getCategoryId());
            hashMap.put("originalPrice",spuDTO.getOriginalPrice());
            hashMap.put("detail",spuDTO.getDetail());
            hashMap.put("description",spuDTO.getDescription());
            hashMap.put("freightTemplateId",spuDTO.getFreightTemplateId());
            hashMap.put("status",spuDTO.getStatus());
            hashMap.put("unit",spuDTO.getUnit());
            hashMap.put("spuId",spuDTO.getId());
            List<SkuDO> skuList = spuDTO.getSkuList();
            if(skuList != null && skuList.size() > 0){
                SkuDO skuDO = skuList.get(0);
                hashMap.put("barCode", skuDO.getBarCode());
                hashMap.put("skuTitle", skuDO.getTitle());
            }
            list.add(hashMap);
        }

        SXSSFWorkbook book = new SXSSFWorkbook();
        Sheet sheet1 = book.createSheet("sheet1");
        Row sheet1Row = sheet1.createRow(0);
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        /**
         * 设置列名
         */
        Cell cell = null;
        cell = sheet1Row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(cellStyle);
        for (int i = 1; i <= titles.size(); i++) {
            cell = sheet1Row.createCell(i);
            String str = titles.get(i - 1);
            cell.setCellValue(map.get(str));
            cell.setCellStyle(cellStyle);
        }

        /**
         * 设置列值
         */
        int rows = 1;
        for (Map<String,Object> map : list) {
            Row row = sheet1.createRow(rows++);
//            Class<? extends SpuDTO> clazz = data.getClass();
//            Field[] declaredFields = clazz.getDeclaredFields();

            int initCellNo = 0;
            int titleSize = titles.size();
            for (int i = 0; i <= titleSize; i++) {
                if(i == 0){
                    row.createCell(initCellNo).setCellValue(i);
                }else{
                    String key = titles.get(i-1);
                    /*Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);
                    Object object = field.get(data);*/
                    Object object = map.get(key);
                    if (object == null) {
                        row.createCell(initCellNo).setCellValue("");
                    } else {
                        row.createCell(initCellNo).setCellValue(String.valueOf(object));
                    }
                }
                initCellNo++;
            }
        }
        return book;
    }
    /**
     * 商品属性
     *
     * @param response
     */
    @RequestMapping(value = "/attr", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> getAttribute(){
        return map;
    }
}


