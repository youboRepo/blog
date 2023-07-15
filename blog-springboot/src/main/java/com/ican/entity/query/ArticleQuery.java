package com.ican.entity.query;

import com.ican.common.model.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * 文章查询类
 * 
 * @author youbo
 * @date 2023/7/15/19:51
 */
@Data
public class ArticleQuery extends PageQuery {
    /**
     * 主键标识列表查询
     */
    private List<Integer> ids;

    /**
     * 文章名称模糊查询
     */
    private String keyword;

    /**
     * 文章类型等于查询
     */
    private Integer articleType;

    /**
     * 文章标签等于查询
     */
    private Integer tagId;

    /**
     * 文章类型等于查询
     */
    private Integer categoryId;

    /**
     * 导出类型等于查询
     */
    private Integer exportType;
}
