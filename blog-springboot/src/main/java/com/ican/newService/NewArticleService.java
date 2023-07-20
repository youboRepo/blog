package com.ican.newService;

import com.ican.entity.custom.ArticleCustom;
import com.ican.entity.query.ArticleQuery;
import com.ican.model.vo.PageResult;

import java.util.List;

/**
 * 文章服务层实现
 *
 * @author youbo
 * @date 2023/07/15
 */

public interface NewArticleService {
    PageResult<ArticleCustom> getArticlePageList(ArticleQuery query);

    List<ArticleCustom> getArticleList(ArticleQuery query);

    List<ArticleCustom> getArticleList(List<Integer> ids);
}
