package com.ican.newService.impl;

import com.ican.common.jdbc.MyServiceImpl;
import com.ican.entity.Category;
import com.ican.mapper.CategoryMapper;
import com.ican.model.vo.CategoryVO;
import com.ican.newService.NewCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章分类控制层实现
 * 
 * @author youbo
 * @date 2023/7/15/20:18
 */
@Service
public class NewNewCategoryServiceImpl extends MyServiceImpl<CategoryMapper, Category> implements NewCategoryService {

    @Override
    public List<CategoryVO> getCategoryList(List<Integer> ids) {
        return this.list(ids, CategoryVO.class);
    }
}
