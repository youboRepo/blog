package com.ican.newService;

import com.ican.common.model.PageDTO;
import com.ican.entity.custom.ArticleCustom;
import com.ican.entity.query.ArticleQuery;

import java.util.List;

/**
 * 文章服务层实现
 *
 * @author youbo
 * @date 2023/07/15
 */

public interface NewArticleService {
    PageDTO<ArticleCustom> getArticlePageList(ArticleQuery query);

    List<ArticleCustom> getArticleList(ArticleQuery query);

    List<ArticleCustom> getArticleList(List<Integer> ids);
}
