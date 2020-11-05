package com.kxmall.market.app.api.api.category;

import com.kxmall.market.biz.service.category.CategoryBizService;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by admin on 2019/7/2.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryBizService categoryBizService;

    @Override
    public List<CategoryDTO> categoryList() throws ServiceException {
        return categoryBizService.categoryList();
    }

    /**
     * @param categoryId
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Long> getCategoryFamily(Long categoryId) throws ServiceException {
        return categoryBizService.getCategoryFamily(categoryId);
    }
}
