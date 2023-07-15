package com.ican.newService;

import com.ican.model.vo.CategoryVO;

import java.util.List;

/**
 * 文章类型服务类
 * 
 * @author youbo
 * @date 2023/7/15/20:18
 */
public interface NewCategoryService {
    List<CategoryVO> getCategoryList(List<Integer> ids);
}
