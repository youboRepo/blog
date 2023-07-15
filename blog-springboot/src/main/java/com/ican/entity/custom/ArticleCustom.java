package com.ican.entity.custom;

import com.ican.entity.Article;
import lombok.Data;

/**
 * 文章定制类
 * 
 * @author youbo
 * @date 2023/7/15/20:27
 */
@Data
public class ArticleCustom extends Article {

    /**
     * 分类名字
     */
    private String categoryName;
}
