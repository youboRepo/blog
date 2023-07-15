package com.ican.newService.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ican.common.jdbc.MyServiceImpl;
import com.ican.common.model.PageDTO;
import com.ican.entity.Article;
import com.ican.entity.custom.ArticleCustom;
import com.ican.entity.query.ArticleQuery;
import com.ican.mapper.ArticleMapper;
import com.ican.newService.NewArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章服务层
 *
 * @author youbo
 * @date 2023/07/15
 */

@Service
public class NewNewArticleServiceImpl extends MyServiceImpl<ArticleMapper, Article> implements NewArticleService {
    @Override
    public PageDTO<ArticleCustom> getArticlePageList(ArticleQuery query) {
        return this.page(query, this::setQueryWrapper, ArticleCustom.class);
    }

    @Override
    public List<ArticleCustom> getArticleList(ArticleQuery query) {
        return this.list(query, this::setQueryWrapper, ArticleCustom.class);
    }

    @Override
    public List<ArticleCustom> getArticleList(List<Integer> ids) {
        return this.list(ids, ArticleCustom.class);
    }

    private LambdaQueryWrapper<Article> setQueryWrapper(ArticleQuery query) {
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();

        // 默认根据主键逆序
        queryWrapper.orderByDesc(Article::getId);

        if (query == null) {
            return queryWrapper;
        }

        this.eq(queryWrapper, Article::getCategoryId, query.getCategoryId());
        this.eq(queryWrapper, Article::getArticleType, query.getArticleType());

        this.in(queryWrapper, Article::getId, query.getIds());

        this.like(queryWrapper, Article::getArticleTitle, query.getKeyword());

        queryWrapper.inSql(query.getTagId() != null, Article::getId,
            "SELECT article_id FROM t_article_tag WHERE tag_id =" + query.getTagId());

        return queryWrapper;
    }
}
